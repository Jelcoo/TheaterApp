package me.jelco.theaterapp.controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.jelco.theaterapp.TheaterApplication;
import me.jelco.theaterapp.data.Database;
import me.jelco.theaterapp.models.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowingsController implements Initializable {    User loggedInUser;
    Database database;
    Stage stage;
    Scene scene;

    public ShowingsController(User user, Database database, Stage stage) throws IOException {
        this.loggedInUser = user;
        this.database = database;
        this.stage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(TheaterApplication.class.getResource("showings-view.fxml"));
        fxmlLoader.setController(this);
        this.scene = new Scene(fxmlLoader.load(), 1000, 700);
    }

    public void show() {
        stage.setTitle("Theater Manager - Showings");
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
