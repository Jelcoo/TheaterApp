package me.jelco.theaterapp.controllers;

import javafx.scene.*;
import javafx.scene.layout.*;
import me.jelco.theaterapp.data.*;
import me.jelco.theaterapp.models.*;

public class BaseController {
    protected UserLogin userLogin;
    protected User loggedInUser;
    protected Database database;
    protected VBox layout;
    protected Scene scene;

    public BaseController(UserLogin userLogin, Database database, VBox layout) {
        this.userLogin = userLogin;
        this.database = database;
        this.layout = layout;

        this.loggedInUser = userLogin.getLoggedInUser();
    }

    public void show() {
        if (layout.getChildren().size() > 1)
            layout.getChildren().remove(1);
        layout.getChildren().add(scene.getRoot());
    }
}
