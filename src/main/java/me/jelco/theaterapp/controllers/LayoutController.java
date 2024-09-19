package me.jelco.theaterapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.jelco.theaterapp.data.Database;
import me.jelco.theaterapp.models.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LayoutController implements Initializable {
    User loggedInUser;
    Database database;
    Stage stage;

    @FXML
    VBox layout;

    public LayoutController(User user, Database database, Stage stage) throws IOException {
        this.loggedInUser = user;
        this.database = database;
        this.stage = stage;
    }

    public void onSalesClick(ActionEvent event) throws IOException {
        SalesController salesController = new SalesController(loggedInUser, database, stage);
        salesController.show();
    }

    public void onShowingsClick(ActionEvent event) throws IOException {
        ShowingsController showingsController = new ShowingsController(loggedInUser, database, stage);
        showingsController.show();
    }

    public void onTicketsClick(ActionEvent event) throws IOException {
        TicketsController ticketsController = new TicketsController(loggedInUser, database, stage);
        ticketsController.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            LoginController loginController = new LoginController(loggedInUser, database, stage, layout);
            loginController.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
