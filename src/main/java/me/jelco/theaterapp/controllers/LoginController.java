package me.jelco.theaterapp.controllers;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import me.jelco.theaterapp.*;
import me.jelco.theaterapp.data.*;
import me.jelco.theaterapp.models.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class LoginController implements Initializable {
    UserLogin userLogin;
    Database database;
    Scene scene;
    VBox layout;

    @FXML
    Text messageText;
    @FXML
    TextField username;
    @FXML
    TextField password;
    @FXML
    Button loginButton;

    Button homeButton;
    Button ticketsButton;
    Button showingsButton;
    Button salesButton;

    public LoginController(UserLogin userLogin, Database database, VBox layout) throws IOException {
        this.userLogin = userLogin;
        this.database = database;
        this.layout = layout;

        FXMLLoader fxmlLoader = new FXMLLoader(TheaterApplication.class.getResource("login-view.fxml"));
        fxmlLoader.setController(this);
        this.scene = new Scene(fxmlLoader.load(), 1000, 700);
    }

    public void show() {
        if (layout.getChildren().size() > 1)
            layout.getChildren().remove(1);
        layout.getChildren().add(scene.getRoot());

        layout.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginButton.fire();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeButton = (Button) this.layout.lookup("#homeButton");
        ticketsButton = (Button) this.layout.lookup("#ticketsButton");
        showingsButton = (Button) this.layout.lookup("#showingsButton");
        salesButton = (Button) this.layout.lookup("#salesButton");

        showActionControls(null);
    }

    public void onLoginButtonClick(ActionEvent event) throws IOException {
        String username = this.username.getText();
        String password = this.password.getText();

        User userToLogin = userLogin.validateUser(username, password);
        if (userToLogin == null) {
            messageText.setText("Username or password is incorrect");
            return;
        }

        HomeController homeController = new HomeController(userLogin, database, layout);
        homeController.show();

        showActionControls(userToLogin.getRole());
    }

    private void showActionControls(Role role) {
        if (role == Role.Management) {
            homeButton.setVisible(true);
            ticketsButton.setVisible(true);
            showingsButton.setVisible(true);
            salesButton.setVisible(true);
        } else if (role == Role.Sales) {
            homeButton.setVisible(true);
            ticketsButton.setVisible(true);
        } else {
            homeButton.setVisible(false);
            ticketsButton.setVisible(false);
            showingsButton.setVisible(false);
            salesButton.setVisible(false);
        }
    }
}
