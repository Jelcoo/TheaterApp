package me.jelco.theaterapp.models;

import me.jelco.theaterapp.tools.*;

import java.io.*;
import java.time.*;
import java.util.*;

public class Showing implements Serializable {
    private final List<Sale> sales;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Room room;

    public Showing(String title, LocalDateTime startTime, LocalDateTime endTime, Room room, List<Sale> sales) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.sales = sales;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getFormattedStartTime() {
        return FormattingTools.formatDateTime(startTime);
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getFormattedEndTime() {
        return FormattingTools.formatDateTime(endTime);
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void addSale(Sale sale) {
        sales.add(sale);
    }

    public String getRoomFormatted() {
        return room.getName() + " (" + (this.room.getSeats() - this.calculateOccupiedSeats()) + "/" + room.getSeats() + " seats)";
    }

    public int calculateOccupiedSeats() {
        int occupied = 0;
        for (Sale sale : sales) {
            occupied += sale.getTickets().size();
        }
        return occupied;
    }
}
