package me.jelco.theaterapp.models;

import java.time.LocalDateTime;
import java.util.List;

public class Sale {
    private LocalDateTime soldDate;
    private String customer;
    private List<Seat> seats;

    public Sale(LocalDateTime soldDate, String customer, List<Seat> seats) {
        this.soldDate = soldDate;
        this.customer = customer;
        this.seats = seats;
    }

    public LocalDateTime getSoldDate() {
        return soldDate;
    }
    public String getCustomer() {
        return customer;
    }
    public List<Seat> getTickets() {
        return seats;
    }
}
