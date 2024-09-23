package me.jelco.theaterapp.models;

import java.time.LocalDateTime;
import java.util.List;

public class Sale {
    private LocalDateTime soldDate;
    private String customer;
    private List<Ticket> tickets;
    private Showing showing;

    public Sale(LocalDateTime soldDate, String customer, List<Ticket> tickets, Showing showing) {
        this.soldDate = soldDate;
        this.customer = customer;
        this.tickets = tickets;
        this.showing = showing;
    }

    public LocalDateTime getSoldDate() {
        return soldDate;
    }
    public String getCustomer() {
        return customer;
    }
    public List<Ticket> getTickets() {
        return tickets;
    }
    public Showing getShowing() {
        return showing;
    }
}
