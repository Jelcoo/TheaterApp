package me.jelco.theaterapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.jelco.theaterapp.controllers.LoginController;
import me.jelco.theaterapp.data.Database;
import me.jelco.theaterapp.models.User;

import java.io.IOException;

public class TheaterApplication extends Application {
    Database database;
    User loggedInUser;

    public TheaterApplication() {
        database = new Database();
    }

    @Override
    public void start(Stage stage) throws IOException {
        LoginController loginController = new LoginController(loggedInUser, database, stage);
        loginController.show();
        stage.setTitle("Theater Manager");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}