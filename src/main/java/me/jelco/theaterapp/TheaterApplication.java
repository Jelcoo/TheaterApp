package me.jelco.theaterapp;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import me.jelco.theaterapp.controllers.*;
import me.jelco.theaterapp.data.*;
import me.jelco.theaterapp.tools.*;

import java.io.*;

public class TheaterApplication extends Application {
    Database database;
    UserLogin userLogin;

    @Override
    public void start(Stage stage) throws IOException {
        database = IOTools.getDatabase();
        userLogin = new UserLogin(database);

        FXMLLoader fxmlLoader = new FXMLLoader(TheaterApplication.class.getResource("layout.fxml"));
        fxmlLoader.setController(new LayoutController(userLogin, database));
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
