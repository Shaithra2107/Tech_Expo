package com.example.cw_1601_sem2;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class UserVerification {
    private static final String PASSWORD = "password"; // Replace with your password

    public static boolean verifyUser() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("User Verification");
        dialog.setHeaderText("Access Restricted");
        dialog.setContentText("Please enter the password:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && result.get().equals(PASSWORD)) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect password. Access denied.Sarah can only access!!!", ButtonType.OK);
            alert.showAndWait();
            return false;
        }
    }
}
