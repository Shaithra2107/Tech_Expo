package com.example.cw_1601_sem2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public Button button;
    @FXML
    private StackPane contentArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Loading the initial scene
            loadFXML("home.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to load home.fxml: " + ex.getMessage());
        }
    }

    @FXML
    public void home(ActionEvent event) {
        try {
            loadFXML("home.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to load home.fxml: " + ex.getMessage());
        }
    }

    public void APD(ActionEvent event) {
        try {
            loadFXML("Adding_Projects.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to load Adding_Projects.fxml: " + ex.getMessage());
        }
    }

    public void UPD(ActionEvent event) {
        try {
            loadFXML("Update_Project_Detail.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to load Update_Project_Detail.fxml: " + ex.getMessage());
        }
    }

    public void DPD(ActionEvent event) {
        try {
            loadFXML("Delete_Project.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to load Delete_Project.fxml: " + ex.getMessage());
        }
    }

    public void VPD(ActionEvent event) {
        try {
            loadFXML("View_Project_Details.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to load View_Project_Details.fxml: " + ex.getMessage());
        }
    }

    @FXML
    public void Start(ActionEvent event) {
        try {
            loadFXML("Start.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to load Start.fxml: " + ex.getMessage());
        }
    }

    protected void loadFXML(String fxmlFileName) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource(fxmlFileName));
        contentArea.getChildren().clear();
        contentArea.getChildren().setAll(fxml);
    }

    @FXML
    public void exit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Press OK to exit or Cancel to stay.");

        // Customizing the buttons in the alert dialog
        ButtonType okButton = new ButtonType("OK");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(okButton, cancelButton);

        // Handling user's choice
        alert.showAndWait().ifPresent(response -> {
            if (response == okButton) {
                // Close the application
                Stage stage = (Stage) contentArea.getScene().getWindow();
                stage.close();
            }
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
