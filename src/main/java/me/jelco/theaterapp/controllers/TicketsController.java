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

    @FXML
    private TableView<Showing> showsTable;
    @FXML
    private Button selectButton;
    @FXML
    private Text selectedText;

    public TicketsController(UserLogin userLogin, Database database, VBox layout) throws IOException {
        super(userLogin, database, layout);

        this.scene = UITools.loadScene(this, "tickets-view.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Showing> showings = FXCollections.observableArrayList(database.getShowings());
        showsTable.setItems(showings);

        // Listen for selected row change
        showsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectButton.setDisable(false);
                selectedShowing = newValue;
                selectedText.setText("Selected: " + FormattingTools.formatDateTime(selectedShowing.getStartTime()) + " - " + selectedShowing.getTitle());
            }
        });
    }

    public void onSelectClick(ActionEvent event) throws IOException {
        TicketsSelectorController ticketsSelectorController = new TicketsSelectorController(userLogin, database, layout, selectedShowing);
        ticketsSelectorController.show();
    }
}
