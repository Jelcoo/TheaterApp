package me.jelco.theaterapp.controllers;

import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import me.jelco.theaterapp.data.*;
import me.jelco.theaterapp.models.*;
import me.jelco.theaterapp.tools.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class SalesController extends BaseController implements Initializable {
    @FXML
    private TableView<Sale> salesTable;

    public SalesController(UserLogin userLogin, Database database, VBox layout) throws IOException {
        super(userLogin, database, layout);

        this.scene = UITools.loadScene(this, "sales-view.fxml");
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
