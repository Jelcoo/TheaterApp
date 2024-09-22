package me.jelco.theaterapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    @FXML
    Button homeButton;
    @FXML
    Button ticketsButton;
    @FXML
    Button showingsButton;
    @FXML
    Button salesButton;

    public LayoutController(User user, Database database, Stage stage) throws IOException {
        this.loggedInUser = user;
        this.database = database;
        this.stage = stage;
    }

    public void onHomeClick(ActionEvent actionEvent) throws IOException {
        HomeController homeController = new HomeController(loggedInUser, database, layout);
        homeController.show();
    }

    public void onSalesClick(ActionEvent event) throws IOException {
        SalesController salesController = new SalesController(loggedInUser, database, layout);
        salesController.show();
    }

    public void onShowingsClick(ActionEvent event) throws IOException {
        ShowingsController showingsController = new ShowingsController(loggedInUser, database, layout);
        showingsController.show();
    }

    public void onTicketsClick(ActionEvent event) throws IOException {
        TicketsController ticketsController = new TicketsController(loggedInUser, database, layout);
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
        homeButton.setVisible(false);
        ticketsButton.setVisible(false);
        showingsButton.setVisible(false);
        salesButton.setVisible(false);
    }
}
