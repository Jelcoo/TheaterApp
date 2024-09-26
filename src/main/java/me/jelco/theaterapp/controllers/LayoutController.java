package me.jelco.theaterapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.jelco.theaterapp.data.Database;
import me.jelco.theaterapp.data.UserLogin;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
