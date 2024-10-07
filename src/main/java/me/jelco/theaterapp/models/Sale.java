package me.jelco.theaterapp.models;

import me.jelco.theaterapp.tools.*;

import java.io.*;
import java.time.*;
import java.util.*;

public class Sale implements Serializable {
    private String showingTitle;
    private LocalDateTime soldDate;
    private String customer;
    private List<Seat> seats;

    public Sale(String showingTitle, LocalDateTime soldDate, String customer, List<Seat> seats) {
        this.showingTitle = showingTitle;
        this.soldDate = soldDate;
        this.customer = customer;
        this.seats = seats;
    }

    public String getShowingTitle() {
        return showingTitle;
    }
    public String getSoldDate() {
        return FormattingTools.formatDateTime(soldDate);
    }
    public String getCustomer() {
        return customer;
    }
    public List<Seat> getTickets() {
        return seats;
    }
    public String getTicketsCount() {
        return String.valueOf(seats.size());
    }
}
