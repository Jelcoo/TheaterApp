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
import me.jelco.theaterapp.tools.*;

import java.io.*;
import java.net.*;
import java.time.*;
import java.util.*;

public class ShowingsDialogController implements Initializable {
    @FXML
    private TextField titleField;
    @FXML
    private ComboBox<Room> roomSelector;
    @FXML
    private DatePicker startDateField;
    @FXML
    private TextField startTimeField;
    @FXML
    private DatePicker endDateField;
    @FXML
    private TextField endTimeField;
    @FXML
    private Text errorLabel;

    private final Database database;
    private Showing showing;

    public ShowingsDialogController(Database database, Showing showing) {
        this.database = database;
        this.showing = showing;
    }

    public Showing getShowing() {
        return showing;
    }

    public void show() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TheaterApplication.class.getResource("showings-dialog.fxml"));
        fxmlLoader.setController(this);

        Scene scene = new Scene(fxmlLoader.load());
        Stage dialog = new Stage();
        dialog.setScene(scene);
        dialog.setTitle("Create/edit showing");
        dialog.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Room> rooms = database.getRooms();
        roomSelector.getItems().addAll(rooms);

        if (showing != null) {
            titleField.setText(showing.getTitle());
            roomSelector.setValue(showing.getRoom());
            startDateField.setValue(showing.getStartTime().toLocalDate());
            startTimeField.setText(FormattingTools.formatTime(showing.getStartTime()));
            endDateField.setValue(showing.getEndTime().toLocalDate());
            endTimeField.setText(FormattingTools.formatTime(showing.getEndTime()));
        }
    }

    public void onSaveClick(ActionEvent event) {
        Showing constructedShowing = constructShowing();
        if (constructedShowing == null) return;
        showing = constructedShowing;

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private Showing constructShowing() {
        String title = titleField.getText();
        if (!isValidTitle(title)) {
            UITools.setError(errorLabel, "The title cannot be empty");
            return null;
        }

        LocalDate startDate = startDateField.getValue();
        if (startDate == null) {
            UITools.setError(errorLabel, "The start date is not valid");
            return null;
        }

        String startTimeValue = startTimeField.getText();
        if (!isValidTime(startTimeValue)) {
            UITools.setError(errorLabel, "The start time is not valid");
            return null;
        }
        LocalTime startTime = formatTime(startTimeValue);

        LocalDate endDate = endDateField.getValue();
        if (endDate == null) {
            UITools.setError(errorLabel, "The end date is not valid");
            return null;
        }

        String endTimeValue = endTimeField.getText();
        if (!isValidTime(endTimeValue)) {
            UITools.setError(errorLabel, "The end time is not valid");
            return null;
        }
        LocalTime endTime = formatTime(endTimeValue);

        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
        if (endDateTime.isBefore(startDateTime)) {
            UITools.setError(errorLabel, "The end date/time is before the start date/time");
            return null;
        }
        if (endDateTime.equals(startDateTime)) {
            UITools.setError(errorLabel, "The end date/time cannot be the same as start date/time");
            return null;
        }

        Room room = roomSelector.getValue();
        if (room == null) {
            UITools.setError(errorLabel, "The room is not valid");
            return null;
        }
        if (showing != null && room.getSeats() < showing.calculateOccupiedSeats()) {
            UITools.setError(errorLabel, "Selected room is smaller than sold tickets");
            return null;
        }

        return new Showing(title, startDateTime, endDateTime, room, showing != null ? showing.getSales() : new ArrayList<>());
    }

    private boolean isValidTitle(String title) {
        return !title.isEmpty();
    }

    private boolean isValidTime(String time) {
        return time.matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$");
    }

    private LocalTime formatTime(String time) {
        return LocalTime.parse(time);
    }
}
