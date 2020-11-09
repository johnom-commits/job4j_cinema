package ru.job4j.cinema.persistence;

import ru.job4j.cinema.domain.Client;
import ru.job4j.cinema.domain.Seat;

public class MemSeatsDAOImp implements SeatsDAO {
    private static final SeatsDAO INST = new MemSeatsDAOImp();
    private Seat[][] seats;

    public MemSeatsDAOImp() {
        Seat seat1 = new Seat(1, new Client(1, "john", "8906760"), 0);
        Seat seat2 = new Seat(2, new Client(2, "Petr", "01"), 0);
        Seat seat3 = new Seat(3, null, 0);
        Seat seat4 = new Seat(4, null, 0);
        Seat seat5 = new Seat(5, null, 0);
        Seat seat6 = new Seat(6, null, 0);
        Seat seat7 = new Seat(7, null, 0);
        Seat seat8 = new Seat(8, null, 0);
        Seat seat9 = new Seat(9, null, 0);
        seats = new Seat[][]{{seat1, seat2, seat3}, {seat4, seat5, seat6}, {seat7, seat8, seat9}};
    }

    public static SeatsDAO instOf() {
        return INST;
    }

    @Override
    public Seat[][] getSeats() {
        return seats;
    }

    @Override
    public void sell(String row, String seat, String username, String phone) {

    }
}
