package me.jelco.theaterapp.controllers;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.stage.*;
import me.jelco.theaterapp.*;
import me.jelco.theaterapp.data.*;
import me.jelco.theaterapp.models.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class TicketSelectorDialogController implements Initializable {
    private final Database database;
    private Showing showing;
    private Sale sale;
    private boolean confirmed = false;

    public boolean getConfirmed() {
        return confirmed;
    }

    @FXML
    private Text movieLabel;
    @FXML
    private Text dateTimeLabel;
    @FXML
    private Text ticketCountLabel;
    @FXML
    private Text nameLabel;
    @FXML
    private CheckBox confirmCheckbox;
    @FXML
    private Button confirmButton;

    public TicketSelectorDialogController(Database database, Showing showing, Sale sale) {
        this.database = database;
        this.showing = showing;
        this.sale = sale;
    }

    public void show() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TheaterApplication.class.getResource("tickets-selector-confirmation-dialog.fxml"));
        fxmlLoader.setController(this);

        Scene scene = new Scene(fxmlLoader.load());
        Stage dialog = new Stage();
        dialog.setScene(scene);
        dialog.setTitle("Confirm age requirement");
        dialog.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        movieLabel.setText("Showing: " + showing.getTitle());
        dateTimeLabel.setText("Date and time: " + showing.getFormattedStartTime());
        ticketCountLabel.setText("Number of tickets: " + sale.getTicketsCount());
        nameLabel.setText("Customer: " + sale.getCustomer());
        confirmCheckbox.setSelected(false);

        // Listen for checkbox check
        confirmCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            confirmButton.setDisable(!newValue);
            confirmed = newValue;
        });
    }

    public void onButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
