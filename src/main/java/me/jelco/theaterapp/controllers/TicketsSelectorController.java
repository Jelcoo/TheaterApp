package me.jelco.theaterapp.controllers;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import me.jelco.theaterapp.TheaterApplication;
import me.jelco.theaterapp.data.Database;
import me.jelco.theaterapp.data.UserLogin;
import me.jelco.theaterapp.models.Room;
import me.jelco.theaterapp.models.Seat;
import me.jelco.theaterapp.models.Showing;
import me.jelco.theaterapp.models.User;
import me.jelco.theaterapp.tools.FormattingTools;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TicketsSelectorController implements Initializable {
    UserLogin userLogin;
    User loggedInUser;
    Database database;
    Scene scene;
    VBox layout;

    Showing selectedShowing;

    @FXML
    Text selectedText;
    @FXML
    GridPane seatSelector;
    @FXML
    ListView<Seat> seatList;
    @FXML
    TextField customerInput;

    public TicketsSelectorController(UserLogin userLogin, Database database, VBox layout, Showing showing) throws IOException {
        this.userLogin = userLogin;
        this.database = database;
        this.layout = layout;
        this.selectedShowing = showing;

        this.loggedInUser = userLogin.getLoggedInUser();

        FXMLLoader fxmlLoader = new FXMLLoader(TheaterApplication.class.getResource("tickets-selector-view.fxml"));
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
        selectedText.setText("Selected: " + FormattingTools.formatDateTime(selectedShowing.getStartTime()) + " - " + selectedShowing.getTitle());

        Room showingRoom = selectedShowing.getRoom();
        int roomRows = showingRoom.getRows();
        int roomColumns = showingRoom.getColumns();

        setGridSize(roomRows, roomColumns + 1);
        for (int i = 0; i < roomRows; i++) {
            for (int j = 0; j < roomColumns + 1; j++) {
                if (j == 0) {
                    Label rowLabel = new Label();
                    rowLabel.setText("Row " + (i + 1));
                    seatSelector.add(rowLabel, 0, i);
                    continue;
                }
                seatSelector.add(getSeatButton(j), j, i);
            }
        }
    }
    private void setGridSize(int rows, int cols) {
        seatSelector.getRowConstraints().clear();
        seatSelector.getColumnConstraints().clear();

        for (int i = 0; i < rows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0 / rows);
            seatSelector.getRowConstraints().add(rowConstraints);
        }
        for (int j = 0; j < cols; j++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100.0 / cols);
            seatSelector.getColumnConstraints().add(columnConstraints);
        }
    }
    private Button getSeatButton(int seatNumber) {
        Button button = new Button();
        button.setText(String.valueOf(seatNumber));
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setHgrow(button, Priority.ALWAYS);
        GridPane.setVgrow(button, Priority.ALWAYS);

        return button;
    }

    public void onSellClick(ActionEvent event) throws IOException {

    }

    public void onCancelClick(ActionEvent event) throws IOException {
        returnToHome();
    }
    private void returnToHome() throws IOException {
        TicketsController ticketsController = new TicketsController(userLogin, database, layout);
        ticketsController.show();
    }
}
