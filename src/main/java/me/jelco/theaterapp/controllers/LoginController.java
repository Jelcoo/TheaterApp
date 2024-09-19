package me.jelco.theaterapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import me.jelco.theaterapp.TheaterApplication;
import me.jelco.theaterapp.data.Database;
import me.jelco.theaterapp.models.User;

import java.io.IOException;
import java.util.List;

public class LoginController {
    User loggedInUser;
    Database database;
    Stage stage;
    Scene scene;

    @FXML
    Text messageText;
    @FXML
    TextField username;
    @FXML
    TextField password;

    public LoginController(User user, Database database, Stage stage) throws IOException {
        this.loggedInUser = user;
        this.database = database;
        this.stage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(TheaterApplication.class.getResource("login-view.fxml"));
        fxmlLoader.setController(this);
        this.scene = new Scene(fxmlLoader.load(), 1000, 700);
    }

    public void show() {
        stage.setTitle("Theater Manager - Login");
        stage.setScene(scene);
    }

    public void onLoginButtonClick(ActionEvent event) throws IOException {
        String username = this.username.getText();
        String password = this.password.getText();

        User userToLogin = validateUser(username, password);
        if (userToLogin == null) {
            messageText.setText("Username or password is incorrect");
            return;
        }
        loggedInUser = userToLogin;
        HomeController homeController = new HomeController(loggedInUser, database, stage);
        homeController.show();
    }

    public User validateUser(String username, String password) {
        List<User> users = database.getUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
