package me.jelco.theaterapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.jelco.theaterapp.controllers.LayoutController;
import me.jelco.theaterapp.data.Database;
import me.jelco.theaterapp.data.UserLogin;
import me.jelco.theaterapp.tools.IOTools;

import java.io.*;

public class TheaterApplication extends Application {
    Database database;
    UserLogin userLogin;

    @Override
    public void start(Stage stage) throws IOException {
        database = IOTools.getDatabase();
        userLogin = new UserLogin(database);

        FXMLLoader fxmlLoader = new FXMLLoader(TheaterApplication.class.getResource("layout.fxml"));
        fxmlLoader.setController(new LayoutController(userLogin, database, stage));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() {
        IOTools.saveDatabase(database);
    }
}
