package me.jelco.theaterapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.jelco.theaterapp.controllers.LayoutController;
import me.jelco.theaterapp.data.Database;
import me.jelco.theaterapp.data.UserLogin;

import java.io.IOException;

public class TheaterApplication extends Application {
    Database database;
    UserLogin userLogin;

    public TheaterApplication() {
        database = new Database();
        userLogin = new UserLogin(database);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TheaterApplication.class.getResource("layout.fxml"));
        fxmlLoader.setController(new LayoutController(userLogin, database, stage));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
