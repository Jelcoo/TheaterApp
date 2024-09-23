package me.jelco.theaterapp.data;

import me.jelco.theaterapp.models.Role;
import me.jelco.theaterapp.models.Showing;
import me.jelco.theaterapp.models.User;

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

    public Database() {
        // users
        users.add(new User("sales", "test", Role.Sales));
        users.add(new User("manager", "test", Role.Management));

        // showings
        showings.add(new Showing("Rebel Moon - Part Two: The Scargiver", LocalDateTime.parse("2024-10-04T14:00:00"), LocalDateTime.parse("2024-10-04T16:30:00")));
        showings.add(new Showing("Rebel Moon - Part Two: The Scargiver", LocalDateTime.parse("2024-10-05T20:00:00"), LocalDateTime.parse("2024-10-05T22:00:00")));
    }

    public void deleteShowing(Showing showing) {
        showings.remove(showing);
    }
}
