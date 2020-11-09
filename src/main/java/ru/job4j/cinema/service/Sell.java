package ru.job4j.cinema.service;

import ru.job4j.cinema.persistence.PsqlStore;

public class Sell {

    public void sellTicket(String row, String seat, String username, String phone) {
        PsqlStore.instOf().sell(row, seat, username, phone);
    }
}
