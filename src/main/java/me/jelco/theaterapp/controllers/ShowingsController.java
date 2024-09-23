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
import me.jelco.theaterapp.TheaterApplication;
import me.jelco.theaterapp.data.Database;
import me.jelco.theaterapp.data.UserLogin;
import me.jelco.theaterapp.models.Showing;
import me.jelco.theaterapp.models.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowingsController implements Initializable {
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
    Button addButton;
    @FXML
    Button editButton;
    @FXML
    Button deleteButton;
    @FXML
    Text errorLabel;

    public ShowingsController(UserLogin userLogin, Database database, VBox layout) throws IOException {
        this.userLogin = userLogin;
        this.database = database;
        this.layout = layout;

        this.loggedInUser = userLogin.getLoggedInUser();
        if (this.loggedInUser == null) {
            LoginController loginController = new LoginController(userLogin, database, layout);
            loginController.show();
        }

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

        showsTable.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Showing>) (observable, oldValue, newValue) -> {
            if (newValue != null) {
                editButton.setDisable(false);
                deleteButton.setDisable(false);
                selectedShowing = newValue;
            }
        });
    }

    public void onAddClick(ActionEvent event) {

    }
    public void onEditClick(ActionEvent event) {

    }
    public void onDeleteClick(ActionEvent event) {
        if (selectedShowing == null) return;
        if (!database.getShowingSales(selectedShowing).isEmpty()) {
            setError("Cannot delete: there are already tickets sold for this showing");
            return;
        }

        database.deleteShowing(selectedShowing);
        showings.remove(selectedShowing);
    }

    private void setError(String error) {
        errorLabel.setText(error);
        errorLabel.setVisible(true);
    }
}
