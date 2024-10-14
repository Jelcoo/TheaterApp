package me.jelco.theaterapp.controllers;

import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import me.jelco.theaterapp.*;
import me.jelco.theaterapp.data.*;
import me.jelco.theaterapp.models.*;
import me.jelco.theaterapp.tools.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class TicketsController implements Initializable {
    private UserLogin userLogin;
    private User loggedInUser;
    private Database database;
    private Scene scene;
    private VBox layout;

    private Showing selectedShowing;

    @FXML
    private TableView<Showing> showsTable;
    @FXML
    private Button selectButton;
    @FXML
    private Text selectedText;

    public TicketsController(UserLogin userLogin, Database database, VBox layout) throws IOException {
        this.userLogin = userLogin;
        this.database = database;
        this.layout = layout;

        this.loggedInUser = userLogin.getLoggedInUser();

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
