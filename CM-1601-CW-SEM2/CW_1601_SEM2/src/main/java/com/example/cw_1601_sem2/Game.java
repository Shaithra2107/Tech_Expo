package com.example.cw_1601_sem2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Game implements Initializable {

    @FXML
    private StackPane contentArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Loading the initial scene
            loadFXML("start_menu.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void SPR(ActionEvent event) throws IOException {
        loadFXML("Select_Projects_Randomly.fxml");
    }

    public void VWP(ActionEvent event) throws IOException {
        loadFXML("Visualize_Winning_Projects.fxml");
    }
    public void VPD(ActionEvent event) throws IOException {
        loadFXML("View_Project_Details.fxml");
    }

    public void RAR(ActionEvent event) throws IOException {
        // Load the RecordAwardsAndRecognitions view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("record_awards_and_recognitions.fxml"));
        Parent root = loader.load();

        // Get selected projects from SelectProjectsRandomly
        List<Project> selectedProjects = SelectProjectsRandomly.getSelectedProjects();

        // Pass the selected projects to RecordAwardsAndRecognitions controller
        RecordAwardsAndRecognitions controller = loader.getController();
        controller.setSelectedProjects(selectedProjects);

        contentArea.getChildren().clear();
        contentArea.getChildren().add(root);
    }

    private void loadFXML(String fxmlFileName) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource(fxmlFileName));
        contentArea.getChildren().clear();
        contentArea.getChildren().setAll(fxml);
    }

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


}