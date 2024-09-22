package me.jelco.theaterapp.controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.jelco.theaterapp.TheaterApplication;
import me.jelco.theaterapp.data.Database;
import me.jelco.theaterapp.data.UserLogin;
import me.jelco.theaterapp.models.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TicketsController implements Initializable {
    UserLogin userLogin;
    User loggedInUser;
    Database database;
    Scene scene;
    VBox layout;

    public TicketsController(UserLogin userLogin, Database database, VBox layout) throws IOException {
        this.userLogin = userLogin;
        this.database = database;
        this.layout = layout;

        this.loggedInUser = userLogin.getLoggedInUser();
        if (this.loggedInUser == null) {
            LoginController loginController = new LoginController(userLogin, database, layout);
            loginController.show();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(TheaterApplication.class.getResource("tickets-view.fxml"));
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
    }
}
