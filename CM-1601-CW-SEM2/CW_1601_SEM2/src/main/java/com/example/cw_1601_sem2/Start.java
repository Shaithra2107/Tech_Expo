package com.example.cw_1601_sem2;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class Start {

    public void Start_Game(ActionEvent actionEvent) {
        try {
            // Display information alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Alert");
            alert.setHeaderText("Cannot Go Back");
            alert.setContentText("You cannot go back to the previous page.");
            alert.showAndWait();

            // Load Game.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Game.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Create a new scene with the loaded root and set the scene to the stage
            Scene scene = new Scene(root,900,600);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace for debugging
            showErrorAlert("Failed to load Game.fxml", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace for debugging
            showErrorAlert("Unexpected error", e.getMessage());
        }
    }

    private void showErrorAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
