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
    private List<Seat> seats = new ArrayList<>();
    public List<Seat> getTickets() {
        return seats;
    }
    private List<Sale> sales = new ArrayList<>();
    public List<Sale> getSales() {
        return sales;
    }
    private List<Room> rooms = new ArrayList<>();
    public List<Room> getRooms() {
        return rooms;
    }

    public Database() {
        // users
        users.add(new User("sales", "test", Role.Sales));
        users.add(new User("manager", "test", Role.Management));

        // rooms
        Room room1 = new Room("Room 1", 6, 12);
        Room room2 = new Room("Room 2", 5, 20);
        rooms.add(room1);
        rooms.add(room2);

        // tickets
        Seat seat29 = new Seat(2, 9);
        Seat seat210 = new Seat(2, 10);
        Seat seat34 = new Seat(3, 4);
        Seat seat35 = new Seat(3, 5);
        Seat seat36 = new Seat(3, 6);
        Seat seat37 = new Seat(3, 7);
        seats.add(seat29);
        seats.add(seat210);
        seats.add(seat34);
        seats.add(seat35);
        seats.add(seat36);
        seats.add(seat37);

        // sales
        List<Seat> sale1Seats = new ArrayList<>();
        sale1Seats.add(seat34);
        sale1Seats.add(seat35);
        sale1Seats.add(seat36);
        sale1Seats.add(seat37);
        Sale sale1 = new Sale(LocalDateTime.parse("2024-10-02T16:35:00"), "Wim Wittenburg", sale1Seats);
        List<Seat> sale2Seats = new ArrayList<>();
        sale2Seats.add(seat29);
        sale2Seats.add(seat210);
        Sale sale2 = new Sale(LocalDateTime.parse("2024-10-02T17:02:00"), "Erwin de Vries", sale2Seats);
        sales.add(sale1);
        sales.add(sale2);

        // showings
        List<Sale> rm1004Sales = new ArrayList<>();
        rm1004Sales.add(sale1);
        rm1004Sales.add(sale2);
        Showing rm1004 = new Showing("Rebel Moon - Part Two: The Scargiver", LocalDateTime.parse("2024-10-04T14:00:00"), LocalDateTime.parse("2024-10-04T16:30:00"), room1, rm1004Sales);
        Showing rm1005 = new Showing("Rebel Moon - Part Two: The Scargiver", LocalDateTime.parse("2024-10-05T20:00:00"), LocalDateTime.parse("2024-10-05T22:00:00"), room1, new ArrayList<>());
        showings.add(rm1004);
        showings.add(rm1005);
    }

    public void createShowing(Showing showing) {
        showings.add(showing);
    }
    public void deleteShowing(Showing showing) {
        showings.remove(showing);
    }
}
