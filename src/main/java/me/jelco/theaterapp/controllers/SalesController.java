package me.jelco.theaterapp.controllers;

import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import me.jelco.theaterapp.*;
import me.jelco.theaterapp.data.*;
import me.jelco.theaterapp.models.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class SalesController implements Initializable {
    UserLogin userLogin;
    User loggedInUser;
    Database database;
    Scene scene;
    VBox layout;

    @FXML
    TableView<Sale> salesTable;

    public SalesController(UserLogin userLogin, Database database, VBox layout) throws IOException {
        this.userLogin = userLogin;
        this.database = database;
        this.layout = layout;

        this.loggedInUser = userLogin.getLoggedInUser();

        FXMLLoader fxmlLoader = new FXMLLoader(TheaterApplication.class.getResource("sales-view.fxml"));
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
        List<Showing> showings = database.getShowings();
        List<Sale> allSales = new ArrayList<>();
        for (Showing showing : showings) {
            allSales.addAll(showing.getSales());
        }

        ObservableList<Sale> sales = FXCollections.observableArrayList(allSales);
        salesTable.setItems(sales);
    }
}
