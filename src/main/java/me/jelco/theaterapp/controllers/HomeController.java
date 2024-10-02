package me.jelco.theaterapp.controllers;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import me.jelco.theaterapp.*;
import me.jelco.theaterapp.data.*;
import me.jelco.theaterapp.models.*;

import java.io.*;
import java.net.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.concurrent.*;

public class HomeController implements Initializable {
    UserLogin userLogin;
    User loggedInUser;
    Database database;
    Scene scene;
    VBox layout;

    @FXML
    Text welcomeText;
    @FXML
    Text loggedinAsText;
    @FXML
    Text datetimeText;

    public HomeController(UserLogin userLogin, Database database, VBox layout) throws IOException {
        this.userLogin = userLogin;
        this.database = database;
        this.layout = layout;

        this.loggedInUser = userLogin.getLoggedInUser();

        FXMLLoader fxmlLoader = new FXMLLoader(TheaterApplication.class.getResource("home-view.fxml"));
        fxmlLoader.setController(this);
        this.scene = new Scene(fxmlLoader.load(), 1000, 700);
    }

    public void show() {
        if (layout.getChildren().size() > 1)
            layout.getChildren().remove(1);
        layout.getChildren().add(scene.getRoot());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeText.setText("Welcome " + loggedInUser.getUsername());
        loggedinAsText.setText("You are logged in as " + loggedInUser.getRole());

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            datetimeText.setText("The current date and time is " + formattedDateTime);
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void onLogoutClick(ActionEvent event) throws IOException {
        userLogin.logoutUser();

        LoginController loginController = new LoginController(userLogin, database, layout);
        loginController.show();
    }
}
