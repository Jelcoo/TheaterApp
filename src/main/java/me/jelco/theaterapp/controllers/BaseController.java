package me.jelco.theaterapp.controllers;

import javafx.scene.layout.*;
import me.jelco.theaterapp.data.*;
import me.jelco.theaterapp.models.*;

public class BaseController {
    protected UserLogin userLogin;
    protected User loggedInUser;
    protected Database database;
    protected VBox layout;

    public BaseController(UserLogin userLogin, Database database, VBox layout) {
        this.userLogin = userLogin;
        this.database = database;
        this.layout = layout;

        this.loggedInUser = userLogin.getLoggedInUser();
    }
}
