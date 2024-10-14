package me.jelco.theaterapp.controllers;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import me.jelco.theaterapp.data.*;
import me.jelco.theaterapp.tools.*;

import java.io.*;
import java.net.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.concurrent.*;

public class HomeController extends BaseController implements Initializable {
    private Scene scene;

    @FXML
    private Text welcomeText;
    @FXML
    private Text loggedinAsText;
    @FXML
    private Text datetimeText;

    public HomeController(UserLogin userLogin, Database database, VBox layout) throws IOException {
        super(userLogin, database, layout);

        this.scene = UITools.loadScene(this, "home-view.fxml");
    }

    public void show() {
        if (layout.getChildren().size() > 1)
            layout.getChildren().remove(1);
        layout.getChildren().add(scene.getRoot());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeText.setText("Welcome " + loggedInUser.getUsername());
        loggedinAsText.setText("You are logged in as " + loggedInUser.getRole());

        // Create clock scheduler
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            datetimeText.setText("The current date and time is " + formattedDateTime);
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void onLogoutClick(ActionEvent event) throws IOException {
        userLogin.logoutUser();

        LoginController loginController = new LoginController(userLogin, database, layout);
        loginController.show();
    }
}
