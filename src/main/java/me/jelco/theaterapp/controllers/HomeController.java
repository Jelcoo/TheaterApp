package me.jelco.theaterapp.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import me.jelco.theaterapp.TheaterApplication;
import me.jelco.theaterapp.data.Database;
import me.jelco.theaterapp.models.User;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HomeController implements Initializable {
    User loggedInUser;
    Database database;
    Stage stage;
    Scene scene;

    @FXML
    Text welcomeText;
    @FXML
    Text loggedinAsText;
    @FXML
    Text datetimeText;

    public HomeController(User user, Database database, Stage stage) throws IOException {
        this.loggedInUser = user;
        this.database = database;
        this.stage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(TheaterApplication.class.getResource("home-view.fxml"));
        fxmlLoader.setController(this);
        this.scene = new Scene(fxmlLoader.load(), 1000, 700);
    }

    public void show() {
        stage.setScene(scene);
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
}
