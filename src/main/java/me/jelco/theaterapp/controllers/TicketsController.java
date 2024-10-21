package me.jelco.theaterapp.controllers;

import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import me.jelco.theaterapp.data.*;
import me.jelco.theaterapp.models.*;
import me.jelco.theaterapp.tools.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class TicketsController extends BaseController implements Initializable {
    private Showing selectedShowing;
    private ObservableList<Showing> allShowings;

    @FXML
    private TableView<Showing> showsTable;
    @FXML
    private Button selectButton;
    @FXML
    private Text selectedText;
    @FXML
    private TextField searchInput;

    public TicketsController(UserLogin userLogin, Database database, VBox layout) throws IOException {
        super(userLogin, database, layout);

        this.scene = UITools.loadScene(this, "tickets-view.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allShowings = FXCollections.observableArrayList(database.getShowings());
        showsTable.setItems(allShowings);

        addSelectionListener();
        addSearchListener();
    }
    private void addSelectionListener() {
        // Listen for selected row change
        showsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectButton.setDisable(false);
                selectedShowing = newValue;
                selectedText.setText("Selected: " + FormattingTools.formatDateTime(selectedShowing.getStartTime()) + " - " + selectedShowing.getTitle());
            }
        });
    }
    private void addSearchListener() {
        // Listen for search input
        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() < 3) {
                showsTable.setItems(allShowings);
                return;
            }
            ObservableList<Showing> matchedShowings = FXCollections.observableArrayList(new ArrayList<>());
            for (Showing showing : allShowings) {
                if (showing.getTitle().toLowerCase().contains(newValue.toLowerCase())) {
                    matchedShowings.add(showing);
                }
            }
            showsTable.setItems(matchedShowings);
        });
    }

    public void onSelectClick(ActionEvent event) throws IOException {
        TicketSelectorController ticketsSelectorController = new TicketSelectorController(userLogin, database, layout, selectedShowing);
        ticketsSelectorController.show();
    }
}
