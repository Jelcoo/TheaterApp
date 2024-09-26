package me.jelco.theaterapp.models;

public class Seat {
    private int row;
    private int seat;

    public Seat(int row, int seat) {
        this.row = row;
        this.seat = seat;
    }

    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "Row: " + row + ", Seat: " + seat;
    }
}
