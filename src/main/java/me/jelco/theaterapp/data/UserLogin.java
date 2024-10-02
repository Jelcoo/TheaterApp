package me.jelco.theaterapp.data;

import me.jelco.theaterapp.models.*;

import java.util.*;

public class UserLogin {
    Database database;

    User loggedInUser;

    public UserLogin(Database database) {
        this.database = database;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public User validateUser(String username, String password) {
        List<User> users = database.getUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                setLoggedInUser(user);
                return user;
            }
        }
        return null;
    }
    private void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public void logoutUser() {
        loggedInUser = null;
    }
}
