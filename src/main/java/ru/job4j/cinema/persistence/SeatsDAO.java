package ru.job4j.cinema.persistence;

import ru.job4j.cinema.domain.Seat;

public interface SeatsDAO {
    Seat[][] getSeats();

    void sell(String row, String seat, String username, String phone);
}
