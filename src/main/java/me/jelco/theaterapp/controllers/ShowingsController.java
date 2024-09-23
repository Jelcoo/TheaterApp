package me.jelco.theaterapp.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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

    @FXML
    TableView showsTable;

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
    }
}
