package me.jelco.theaterapp.tools;

import javafx.scene.text.*;

public class UITools {
    public static void setError(Text errorLabel, String error) {
        errorLabel.setText(error);
        errorLabel.setVisible(true);
    }
}
