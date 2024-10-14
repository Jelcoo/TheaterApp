package me.jelco.theaterapp.controllers;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import me.jelco.theaterapp.data.*;
import me.jelco.theaterapp.models.*;
import me.jelco.theaterapp.tools.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class LoginController extends BaseController implements Initializable {
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
        super(userLogin, database, layout);

        this.scene = UITools.loadScene(this, "login-view.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Listen to ENTER key for login
        layout.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginButton.fire();
            }
        });

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
