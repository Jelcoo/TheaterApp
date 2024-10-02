package me.jelco.theaterapp.controllers;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import me.jelco.theaterapp.data.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class LayoutController implements Initializable {
    UserLogin userLogin;
    Database database;
    Stage stage;

    @FXML
    VBox layout;

    public LayoutController(UserLogin userLogin, Database database, Stage stage) {
        this.userLogin = userLogin;
        this.database = database;
        this.stage = stage;
    }

    public void onHomeClick(ActionEvent actionEvent) throws IOException {
        HomeController homeController = new HomeController(userLogin, database, layout);
        homeController.show();
    }

    public void onTicketsClick(ActionEvent event) throws IOException {
        TicketsController ticketsController = new TicketsController(userLogin, database, layout);
        ticketsController.show();
    }

    public void onShowingsClick(ActionEvent event) throws IOException {
        ShowingsController showingsController = new ShowingsController(userLogin, database, layout);
        showingsController.show();
    }

    public void onSalesClick(ActionEvent event) throws IOException {
        SalesController salesController = new SalesController(userLogin, database, layout);
        salesController.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            LoginController loginController = new LoginController(userLogin, database, layout);
            loginController.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
