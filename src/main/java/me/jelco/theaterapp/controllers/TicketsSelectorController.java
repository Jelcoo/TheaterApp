package me.jelco.theaterapp.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import me.jelco.theaterapp.TheaterApplication;
import me.jelco.theaterapp.data.Database;
import me.jelco.theaterapp.data.UserLogin;
import me.jelco.theaterapp.models.*;
import me.jelco.theaterapp.tools.FormattingTools;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TicketsSelectorController implements Initializable {
    UserLogin userLogin;
    User loggedInUser;
    Database database;
    Scene scene;
    VBox layout;

    private List<Seat> occupiedSeats = new ArrayList<>();
    private ObservableList<Seat> saleSeats = FXCollections.observableArrayList();
    Showing selectedShowing;

    @FXML
    Text selectedText;
    @FXML
    GridPane seatSelector;
    @FXML
    ListView<Seat> seatList;
    @FXML
    TextField customerInput;
    @FXML
    Button sellButton;

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

        seatList.setItems(saleSeats);

        for (Sale sale : selectedShowing.getSales()) {
            occupiedSeats.addAll(sale.getTickets());
        }

        Room showingRoom = selectedShowing.getRoom();
        int roomRows = showingRoom.getRows();
        int roomColumns = showingRoom.getColumns();

        initSeatSelector(roomRows, roomColumns);
        updateSellLabel();
    }
    private void initSeatSelector(int roomRows, int roomColumns) {
        setGridSize(roomRows, roomColumns + 1);
        for (int i = 0; i < roomRows; i++) {
            for (int j = 0; j < roomColumns + 1; j++) {
                if (j == 0) {
                    Label rowLabel = new Label();
                    rowLabel.setText("Row " + (i + 1));
                    seatSelector.add(rowLabel, 0, i);
                    continue;
                }
                seatSelector.add(getSeatButton(i, j), j, i);
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
    private boolean seatIsOccupied(Seat seat) {
        for (Seat occupiedSeat : occupiedSeats) {
            if (occupiedSeat.getRow() == seat.getRow() && occupiedSeat.getSeat() == seat.getSeat()) {
                return true;
            }
        }
        return false;
    }
    private Button getSeatButton(int seatRow, int seatNumber) {
        Seat buttonSeat = new Seat(seatRow + 1, seatNumber);

        Button button = new Button();
        button.setText(String.valueOf(seatNumber));
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setHgrow(button, Priority.ALWAYS);
        GridPane.setVgrow(button, Priority.ALWAYS);
        button.setUserData(buttonSeat);

        if (seatIsOccupied(buttonSeat)) {
            button.setBackground(Background.fill(Paint.valueOf("#ff0000")));
        } else {
            button.setBackground(Background.fill(Paint.valueOf("#00ff00")));

            button.setOnAction(actionEvent -> {
                if (saleSeats.contains(buttonSeat)) {
                    button.setBackground(Background.fill(Paint.valueOf("#00ff00")));
                    saleSeats.remove(buttonSeat);
                } else {
                    button.setBackground(Background.fill(Paint.valueOf("#ffff00")));
                    saleSeats.add(buttonSeat);
                }
                updateSellLabel();
            });
        }

        return button;
    }
    public void updateSellLabel() {
        int ticketSize = saleSeats.size();
        String customerName = customerInput.getText();
        sellButton.setDisable(ticketSize == 0 || customerName.isEmpty());
        sellButton.setText("Sell " + ticketSize + " tickets");
    }

    public void onCustomerInput(KeyEvent key) {
        updateSellLabel();
    }

    public void onSellClick(ActionEvent event) throws IOException {
        String customerName = customerInput.getText();
        Sale sale = new Sale(selectedShowing.getTitle(), LocalDateTime.now(), customerName, saleSeats.stream().toList());
        selectedShowing.addSale(sale);
        returnToHome();
    }

    public void onCancelClick(ActionEvent event) throws IOException {
        returnToHome();
    }
    private void returnToHome() throws IOException {
        TicketsController ticketsController = new TicketsController(userLogin, database, layout);
        ticketsController.show();
    }
}
