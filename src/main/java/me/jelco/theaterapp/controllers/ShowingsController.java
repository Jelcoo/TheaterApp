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

public class ShowingsController implements Initializable {
    private UserLogin userLogin;
    private User loggedInUser;
    private Database database;
    private Scene scene;
    private VBox layout;

    private ObservableList<Showing> showings;
    private Showing selectedShowing;

    @FXML
    private TableView<Showing> showsTable;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Text errorLabel;

    public ShowingsController(UserLogin userLogin, Database database, VBox layout) throws IOException {
        this.userLogin = userLogin;
        this.database = database;
        this.layout = layout;

        this.loggedInUser = userLogin.getLoggedInUser();

        FXMLLoader fxmlLoader = new FXMLLoader(TheaterApplication.class.getResource("showings-view.fxml"));
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
        showings = FXCollections.observableArrayList(database.getShowings());
        showsTable.setItems(showings);

        // Listen for selected row change
        showsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                editButton.setDisable(false);
                deleteButton.setDisable(false);
                selectedShowing = newValue;
            }
        });
    }

    public void onAddClick(ActionEvent event) {
        showEditDialog(null);
    }
    public void onEditClick(ActionEvent event) {
        if (selectedShowing == null) return;
        showEditDialog(selectedShowing);
    }
    public void onDeleteClick(ActionEvent event) {
        if (selectedShowing == null) return;
        if (!selectedShowing.getSales().isEmpty()) {
            UITools.setError(errorLabel, "Cannot delete: there are already tickets sold for this showing");
            return;
        }

        database.deleteShowing(selectedShowing);
        showings.remove(selectedShowing);
    }

    private void showEditDialog(Showing showing) {
        try {
            ShowingsDialogController showingsDialogController = new ShowingsDialogController(database, showing);
            showingsDialogController.show();

            Showing dialogShowing = showingsDialogController.getShowing();
            if (showing == null && dialogShowing != null) {
                showings.add(dialogShowing);
                database.createShowing(dialogShowing);
            } else if (dialogShowing != null) {
                int showingIndex = showings.indexOf(selectedShowing);
                editShowing(showing, dialogShowing);
                if (showingIndex != -1) {
                    showings.set(showingIndex, dialogShowing);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void editShowing(Showing oldShowing, Showing newShowing) {
        if (!Objects.equals(oldShowing.getTitle(), newShowing.getTitle())) {
            selectedShowing.setTitle(newShowing.getTitle());
        }

        if (!Objects.equals(oldShowing.getRoom(), newShowing.getRoom())) {
            selectedShowing.setRoom(newShowing.getRoom());
        }

        if (!Objects.equals(oldShowing.getStartTime(), newShowing.getStartTime())) {
            selectedShowing.setStartTime(newShowing.getStartTime());
        }

        if (!Objects.equals(oldShowing.getEndTime(), newShowing.getEndTime())) {
            selectedShowing.setEndTime(newShowing.getEndTime());
        }
    }
}
