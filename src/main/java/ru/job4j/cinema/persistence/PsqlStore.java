package ru.job4j.cinema.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.cinema.domain.Client;
import ru.job4j.cinema.domain.Seat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class PsqlStore implements SeatsDAO {
    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        var cfg = new Properties();
        try (var io = new BufferedReader(new FileReader("db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final SeatsDAO INST = new PsqlStore();
    }

    public static SeatsDAO instOf() {
        return Lazy.INST;
    }

    @Override
    public Seat[][] getSeats() {
        Seat[][] seats = new Seat[3][3];
        int i = 0;
        int j = 0;
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement("SELECT id, status FROM halls ORDER BY id")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    int id = it.getInt("id");
                    int status = it.getInt("status");
                    seats[i][j++] = new Seat(id, null, status);
                    if (j == 3) {
                        i++;
                        j = 0;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return seats;
    }

    @Override
    public void sell(String row, String seat, String username, String phone) {
        try (var con = pool.getConnection();
             var findUser = con.prepareStatement("SELECT id FROM accounts WHERE fio = (?) AND phone = (?)")
        ) {
            con.setAutoCommit(false);
            int client_id = 0;
            findUser.setString(1, username);
            findUser.setString(2, phone);
            try (var set = findUser.executeQuery()) {
                if (set.next()) {
                    client_id = set.getInt("id");
                }
            }
            if (client_id == 0) {
                try (var insertUser = con.prepareStatement("INSERT INTO accounts (fio, phone) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
                    insertUser.setString(1, username);
                    insertUser.setString(2, phone);
                    insertUser.execute();
                    try (var key = insertUser.getGeneratedKeys()) {
                        if (key.next()) {
                            client_id = key.getInt(1);
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            int r = Integer.parseInt(row);
            int s = Integer.parseInt(seat);
            int id_seat = (r - 1) * 3 + s;
            try (var saveTicket = con.prepareStatement("UPDATE halls SET client_id = (?), status = 1 WHERE id = (?)")) {
                saveTicket.setInt(1, client_id);
                saveTicket.setInt(2, id_seat);
                saveTicket.executeUpdate();
                con.commit();
            } catch (SQLException e1) {
                con.rollback();
                e1.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
