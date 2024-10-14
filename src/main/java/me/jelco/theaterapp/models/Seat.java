package me.jelco.theaterapp.models;

import java.io.*;

public class Seat implements Serializable {
    private final int row;
    private final int seat;

    public Seat(int row, int seat) {
        this.row = row;
        this.seat = seat;
    }

    public int getRow() {
        return row;
    }

    public int getSeat() {
        return seat;
    }

    @Override
    public String toString() {
        return "Row: " + row + ", Seat: " + seat;
    }
}
