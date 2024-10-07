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
    private UserLogin userLogin;
    private Database database;
    private Scene scene;
    private VBox layout;

    @FXML
    private Text messageText;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button loginButton;

    private Button homeButton;
    private Button ticketsButton;
    private Button showingsButton;
    private Button salesButton;

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
            toggleButton(homeButton, true);
            toggleButton(ticketsButton, true);
            toggleButton(showingsButton, true);
            toggleButton(salesButton, true);
        } else if (role == Role.Sales) {
            toggleButton(homeButton, true);
            toggleButton(ticketsButton, true);
        } else {
            toggleButton(homeButton, false);
            toggleButton(ticketsButton, false);
            toggleButton(showingsButton, false);
            toggleButton(salesButton, false);
        }
    }
    private void toggleButton(Button button, boolean toggle) {
        button.setVisible(toggle);
        button.setManaged(toggle);
    }
}
