package me.jelco.theaterapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import me.jelco.theaterapp.TheaterApplication;
import me.jelco.theaterapp.data.Database;
import me.jelco.theaterapp.models.Room;
import me.jelco.theaterapp.models.Showing;
import me.jelco.theaterapp.tools.FormattingTools;
import me.jelco.theaterapp.tools.UITools;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShowingsDialogController implements Initializable {
    @FXML
    TextField titleField;
    @FXML
    ComboBox<Room> roomSelector;
    @FXML
    DatePicker startDateField;
    @FXML
    TextField startTimeField;
    @FXML
    DatePicker endDateField;
    @FXML
    TextField endTimeField;
    @FXML
    Text errorLabel;

    private Database database;
    private Showing showing;
    public Showing getShowing() {
        return showing;
    }


    public ShowingsDialogController(Database database, Showing showing) {
        this.database = database;
        this.showing = showing;
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
        if (!isValidTitle(title)) { UITools.setError(errorLabel, "The title cannot be empty"); return null; }

        LocalDate startDate = startDateField.getValue();
        if (startDate == null) { UITools.setError(errorLabel, "The start date is not valid"); return null; }

        String startTimeValue = startTimeField.getText();
        if (!isValidTime(startTimeValue)) { UITools.setError(errorLabel, "The start time is not valid"); return null; }
        LocalTime startTime = formatTime(startTimeValue);

        LocalDate endDate = endDateField.getValue();
        if (endDate == null) { UITools.setError(errorLabel, "The end date is not valid"); return null; }

        String endTimeValue = endTimeField.getText();
        if (!isValidTime(endTimeValue)) { UITools.setError(errorLabel, "The end time is not valid"); return null; }
        LocalTime endTime = formatTime(endTimeValue);

        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
        if (endDateTime.isBefore(startDateTime)) { UITools.setError(errorLabel, "The end date/time is before the start date/time"); return null; }
        if (endDateTime.equals(startDateTime)) { UITools.setError(errorLabel, "The end date/time cannot be the same as start date/time"); return null; }

        Room room = roomSelector.getValue();
        if (room == null) { UITools.setError(errorLabel, "The room is not valid"); return null; }

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
