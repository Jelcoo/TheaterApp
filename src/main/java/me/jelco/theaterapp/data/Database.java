package me.jelco.theaterapp.data;

import me.jelco.theaterapp.models.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<User> users = new ArrayList<>();
    public List<User> getUsers() {
        return users;
    }
    private List<Showing> showings = new ArrayList<>();
    public List<Showing> getShowings() {
        return showings;
    }
    private List<Ticket> tickets = new ArrayList<>();
    public List<Ticket> getTickets() {
        return tickets;
    }
    private List<Sale> sales = new ArrayList<>();
    public List<Sale> getSales() {
        return sales;
    }

    public Database() {
        // users
        users.add(new User("sales", "test", Role.Sales));
        users.add(new User("manager", "test", Role.Management));

        // showings
        Showing rm1004 = new Showing("Rebel Moon - Part Two: The Scargiver", LocalDateTime.parse("2024-10-04T14:00:00"), LocalDateTime.parse("2024-10-04T16:30:00"));
        Showing rm1005 = new Showing("Rebel Moon - Part Two: The Scargiver", LocalDateTime.parse("2024-10-05T20:00:00"), LocalDateTime.parse("2024-10-05T22:00:00"));
        showings.add(rm1004);
        showings.add(rm1005);

        // tickets
        Ticket ticket29 = new Ticket(2, 9);
        Ticket ticket210 = new Ticket(2, 10);
        Ticket ticket34 = new Ticket(3, 4);
        Ticket ticket35 = new Ticket(3, 5);
        Ticket ticket36 = new Ticket(3, 6);
        Ticket ticket37 = new Ticket(3, 7);
        tickets.add(ticket29);
        tickets.add(ticket210);
        tickets.add(ticket34);
        tickets.add(ticket35);
        tickets.add(ticket36);
        tickets.add(ticket37);

        // sales
        List<Ticket> sale1Tickets = new ArrayList<>();
        sale1Tickets.add(ticket34);
        sale1Tickets.add(ticket35);
        sale1Tickets.add(ticket36);
        sale1Tickets.add(ticket37);
        sales.add(new Sale(LocalDateTime.parse("2024-10-02T16:35:00"), "Wim Wittenburg", sale1Tickets, rm1004));
        List<Ticket> sale2Tickets = new ArrayList<>();
        sale2Tickets.add(ticket29);
        sale2Tickets.add(ticket210);
        sales.add(new Sale(LocalDateTime.parse("2024-10-02T17:02:00"), "Erwin de Vries", sale2Tickets, rm1004));
    }

    public void createShowing(Showing showing) {
        showings.add(showing);
    }
    public void deleteShowing(Showing showing) {
        showings.remove(showing);
    }
    public List<Sale> getShowingSales(Showing showing) {
        List<Sale> filteredSales = new ArrayList<>();
        for (Sale sale : sales) {
            if (sale.getShowing() == showing) {
                filteredSales.add(sale);
            }
        }
        return filteredSales;
    }
}
