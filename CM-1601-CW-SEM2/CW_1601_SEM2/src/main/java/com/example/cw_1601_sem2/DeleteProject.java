package com.example.cw_1601_sem2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class DeleteProject {

    @FXML
    private TextField id;

    @FXML
    private TextField validation;

    @FXML
    private Label mylabel;

    @FXML
    private Button deleteButton;

    @FXML
    Text confirm; // Change from private to package-private (default)



    @FXML
    private void initialize() {
        // Initialize the UI elements if needed
        mylabel.setText("");
    }

    @FXML
    public void Delete(ActionEvent actionEvent) {
        String projectId = id.getText().trim();
        String userValidation = validation.getText().trim().toLowerCase();

        // Validate fields
        String idValidationMessage = ProjectValidator.validateId(projectId);
        if (idValidationMessage != null) {
            showAlert(Alert.AlertType.ERROR, "Error", idValidationMessage);
            return;
        }
        // Verify user
        if (!UserVerification.verifyUser()) {
            showAlert(Alert.AlertType.ERROR, "Error", "User verification failed. Cannot delete the project.");
            clearFields();
            return; // Exit the method if user verification fails
        }

        String userValidationMessage = ProjectValidator.validateUserValidation(userValidation);
        if (userValidationMessage != null) {
            showAlert(Alert.AlertType.INFORMATION, "Error", userValidationMessage);
            clearFields();
            return;
        }

        try {
            int projId = Integer.parseInt(projectId);
            if (ProjectUtils.isIdExist(projId)) {
                ProjectUtils.deleteProjectById(projId);
                mylabel.setText("Project deleted successfully.");
                showAlert(Alert.AlertType.INFORMATION, "Success", "Project deleted successfully.");
                clearFields();
            } else {
                mylabel.setText("Project ID not found.");
                showAlert(Alert.AlertType.ERROR, "Error", "Project ID does not exist. Please check the Project ID!");
            }
        } catch (NumberFormatException e) {
            mylabel.setText("Invalid project ID format.");
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid project ID format. Please enter a valid ID.");
        } catch (IOException e) {
            mylabel.setText("An error occurred during deletion.");
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred during deletion.");
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        id.clear();
        validation.clear();
        mylabel.setText("");
    }


}
