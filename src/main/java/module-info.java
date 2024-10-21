module me.jelco.theaterapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports me.jelco.theaterapp;
    opens me.jelco.theaterapp to javafx.fxml;

    exports me.jelco.theaterapp.controllers;
    opens me.jelco.theaterapp.controllers to javafx.fxml;

    exports me.jelco.theaterapp.data;
    opens me.jelco.theaterapp.data to javafx.fxml;

    exports me.jelco.theaterapp.models;
    opens me.jelco.theaterapp.models to javafx.fxml;
}