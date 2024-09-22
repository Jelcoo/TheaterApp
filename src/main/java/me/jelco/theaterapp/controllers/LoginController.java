package me.jelco.theaterapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import me.jelco.theaterapp.TheaterApplication;
import me.jelco.theaterapp.data.Database;
import me.jelco.theaterapp.data.UserLogin;
import me.jelco.theaterapp.models.Role;
import me.jelco.theaterapp.models.User;

import java.io.IOException;

public class LoginController {
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

        homeButton = (Button) this.layout.lookup("#homeButton");
        ticketsButton = (Button) this.layout.lookup("#ticketsButton");
        showingsButton = (Button) this.layout.lookup("#showingsButton");
        salesButton = (Button) this.layout.lookup("#salesButton");
    }

    public void show() {
        if (layout.getChildren().size() > 1)
            layout.getChildren().remove(1);
        layout.getChildren().add(scene.getRoot());
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
        }
    }
}
