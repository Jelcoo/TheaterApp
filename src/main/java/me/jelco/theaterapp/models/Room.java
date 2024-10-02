package me.jelco.theaterapp.models;

import java.io.*;

public class Room implements Serializable {
    private String name;
    private int rows;
    private int columns;

    public Room(String name, int rows, int columns) {
        this.name = name;
        this.rows = rows;
        this.columns = columns;
    }

    public String getName() {
        return name;
    }
    public int getRows() {
        return rows;
    }
    public int getColumns() {
        return columns;
    }
    public int getSeats() {
        return rows * columns;
    }

    @Override
    public String toString() {
        return this.name + " (" + this.getSeats() + " seats)";
    }
}
