package ru.job4j.cinema.service;

import org.jetbrains.annotations.NotNull;
import ru.job4j.cinema.domain.Seat;
import ru.job4j.cinema.persistence.PsqlStore;

public class FormTable {
    public static String getTable(String row, String seat) {
        Seat[][] seats = PsqlStore.instOf().getSeats();

        int rowInt = 1;
        int seatInt = 1;
        // отрабатываем ситуацию первого запроса, при открытии страницы
        if ("".equals(row) & "".equals(seat)) {
            Seat firstFree = findFirstFreeSeat(seats); // по-умолчанию переключатель ставим на первое свободное место
            if (firstFree != null) {
                int id = firstFree.getId();
                rowInt = id / 3;
                rowInt++;
                seatInt = id % 3;
                if (seatInt == 0) {
                    seatInt = 1;
                }
            }
        } else {
            // обновление страницы по таймауту
            rowInt = Integer.parseInt(row);
            seatInt = Integer.parseInt(seat);
        }
        return getHTML(seats, rowInt, seatInt);
    }

    @NotNull
    private static String getHTML(Seat[][] seats, int rowInt, int seatInt) {
        var text = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            text.append("<tr>").append("<th>").append(i + 1).append("</th>");
            for (int j = 0; j < 3; j++) {
                text.append("<td");
                Seat space = seats[i][j];
                var busy = "";
                if (space.getStatus() == 1) {
                    text.append(" class=\"busy\"");
                    busy = " disabled";
                }
                text.append("><input type=\"radio\" name=\"place\" value=")
                        .append("Row_")
                        .append(i + 1)
                        .append("&Seat_")
                        .append(j + 1)
                        .append(busy);
                if (i == (rowInt - 1) & j == (seatInt - 1)) {
                    text.append(" checked");
                }
                text.append("> Row ")
                        .append(i + 1)
                        .append(", Seat ")
                        .append(j + 1)
                        .append("</td>");
            }
            text.append("<tr>");
        }
        return text.toString();
    }

    private static Seat findFirstFreeSeat(Seat[][] seats) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (seats[i][j].getStatus() == 0) {
                    return seats[i][j];
                }
            }
        }
        return null;
    }
}
