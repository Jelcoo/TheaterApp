package me.jelco.theaterapp.controllers;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import me.jelco.theaterapp.TheaterApplication;
import me.jelco.theaterapp.data.Database;
import me.jelco.theaterapp.data.UserLogin;
import me.jelco.theaterapp.models.Showing;
import me.jelco.theaterapp.models.User;
import me.jelco.theaterapp.tools.FormattingTools;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TicketsController implements Initializable {
    UserLogin userLogin;
    User loggedInUser;
    Database database;
    Scene scene;
    VBox layout;

    private ObservableList<Showing> showings;
    Showing selectedShowing;

    @FXML
    TableView showsTable;
    @FXML
    Button selectButton;
    @FXML
    Text selectedText;

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
        showings = FXCollections.observableArrayList(database.getShowings());
        showsTable.setItems(showings);

        showsTable.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Showing>) (observable, oldValue, newValue) -> {
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
