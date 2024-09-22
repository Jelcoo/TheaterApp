package me.jelco.theaterapp.data;

import me.jelco.theaterapp.models.Role;
import me.jelco.theaterapp.models.User;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<User> users = new ArrayList<>();
    public List<User> getUsers() {
        return users;
    }

    public Database() {
        // users
        users.add(new User("sales", "test", Role.Sales));
        users.add(new User("manager", "test", Role.Management));
    }
}
