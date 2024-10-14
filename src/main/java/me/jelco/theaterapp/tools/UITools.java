package me.jelco.theaterapp.tools;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.text.*;
import me.jelco.theaterapp.*;
import me.jelco.theaterapp.controllers.*;

import java.io.*;

public class UITools {
    public static void setError(Text errorLabel, String error) {
        errorLabel.setText(error);
        errorLabel.setVisible(true);
    }

    public static Scene loadScene(BaseController controller, String resource) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TheaterApplication.class.getResource(resource));
        fxmlLoader.setController(controller);
        return new Scene(fxmlLoader.load(), 1000, 700);
    }
}
