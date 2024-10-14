package me.jelco.theaterapp.controllers;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.stage.*;
import me.jelco.theaterapp.*;
import me.jelco.theaterapp.data.*;
import me.jelco.theaterapp.exceptions.*;
import me.jelco.theaterapp.models.*;
import me.jelco.theaterapp.tools.*;

import java.io.*;
import java.net.*;
import java.time.*;
import java.util.*;

public class ShowingsDialogController implements Initializable {
    private final Database database;
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
        try {
            String title = getTitle();
            LocalDate startDate = getDate(startDateField);
            LocalTime startTime = getTime(startTimeField);
            LocalDate endDate = getDate(endDateField);
            LocalTime endTime = getTime(endTimeField);
            Room room = getRoom();

            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
            if (endDateTime.isBefore(startDateTime)) {
                throw new ValidationException("The end date/time is before the start date/time");
            }
            if (endDateTime.equals(startDateTime)) {
                throw new ValidationException("The end date/time cannot be the same as start date/time");
            }

            return new Showing(title, startDateTime, endDateTime, room, showing != null ? showing.getSales() : new ArrayList<>());
        } catch (ValidationException e) {
            UITools.setError(errorLabel, e.getMessage());
            return null;
        }
    }

    private String getTitle() throws ValidationException {
        String title = titleField.getText();
        if (title.isEmpty()) {
            throw new ValidationException("The title cannot be empty");
        }
        return title;
    }

    private LocalDate getDate(DatePicker datePicker) throws ValidationException {
        LocalDate date = datePicker.getValue();
        if (date == null) {
            throw new ValidationException("Entered date is not valid");
        }
        return date;
    }

    private LocalTime getTime(TextField timeField) throws ValidationException {
        String timeValue = timeField.getText();
        if (!isValidTime(timeValue)) {
            throw new ValidationException("Entered time is not valid");
        }
        return LocalTime.parse(timeValue);
    }

    private Room getRoom() throws ValidationException {
        Room room = roomSelector.getValue();
        if (room == null) {
            throw new ValidationException("The room is not valid");
        }
        if (showing != null && room.getSeats() < showing.calculateOccupiedSeats()) {
            throw new ValidationException("Selected room is smaller than sold tickets");
        }
        return room;
    }

    private boolean isValidTime(String time) {
        return time.matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$");
    }
}
