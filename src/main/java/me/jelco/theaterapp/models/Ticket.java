package me.jelco.theaterapp.models;

public class Ticket {
    private int row;
    private int seat;

    public Ticket(int row, int seat) {
        this.row = row;
        this.seat = seat;
    }

    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }
}
