package ru.job4j.cinema.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Seat {
    private int id;
    private Client client;
    private int status;

    public Seat(int id, Client client, int status) {
        this.id = id;
        this.client = client;
        this.status = status;
    }
}
