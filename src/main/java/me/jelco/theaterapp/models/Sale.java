package me.jelco.theaterapp.models;

import javafx.beans.property.*;
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

    public StringProperty showingProperty() {
        return new SimpleStringProperty(showingTitle);
    }
    public StringProperty dateProperty() {
        return new SimpleStringProperty(FormattingTools.formatDateTime(soldDate));
    }
    public StringProperty customerProperty() {
        return new SimpleStringProperty(customer);
    }
    public List<Seat> getTickets() {
        return seats;
    }
    public StringProperty ticketsCountProperty() {
        return new SimpleStringProperty(String.valueOf(seats.size()));
    }
}
