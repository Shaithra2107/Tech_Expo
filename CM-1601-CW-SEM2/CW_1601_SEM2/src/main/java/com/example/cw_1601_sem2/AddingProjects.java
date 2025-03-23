package com.example.cw_1601_sem2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class AddingProjects {

    @FXML
    private ImageView teamLogoImageView;
    @FXML
    private Text valid;
    @FXML
    private Label mylabel;
    @FXML
    ChoiceBox<String> category;
    @FXML
    private Button logo_button;
    @FXML
    TextField detail;
    @FXML
    TextField members;
    @FXML
    TextField country;
    @FXML
    TextField name;
    @FXML
    TextField id;
    private String logoPath; // Store the selected logo path

    @FXML
    public void initialize() {
        // Initialize choice box items
        category.getItems().addAll("AI", "SE", "CS", "DS");
    }

    @FXML
    protected void handleUploadButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            logoPath = selectedFile.getAbsolutePath(); // Store the logo path
            teamLogoImageView.setImage(new Image(selectedFile.toURI().toString())); // Display the image in ImageView
        }
    }

    @FXML
    protected void save(ActionEvent event) {
        String validationMessage = validateFields();
        if (validationMessage != null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", validationMessage);
            return;
        }

        try {
            String idText = id.getText().trim();
            int projectId = Integer.parseInt(idText);
            String projectName = name.getText().trim();
            String[] projectMembers = members.getText().trim().split("\\s*,\\s*");
            String projectDescription = detail.getText().trim();
            String projectCountry = country.getText().trim();
            String projectCategory = category.getValue();

            if (ProjectUtils.isIdExist(projectId)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Project ID already exists!");
                return;
            }

            Project project = new Project(projectId, projectName, projectMembers, projectDescription, projectCountry, projectCategory, logoPath);
            ProjectUtils.addProject(project);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Project details saved successfully!");
            clearFields();

        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace for debugging
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while saving project details!");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Project ID format!");
        }
    }



    private String validateFields() {
        String idValidation = ProjectValidator.validateId(id.getText());
        if (idValidation != null) return idValidation;

        String nameValidation = ProjectValidator.validateName(name.getText());
        if (nameValidation != null) return nameValidation;

        String membersValidation = ProjectValidator.validateMembers(members.getText());
        if (membersValidation != null) return membersValidation;

        String detailValidation = ProjectValidator.validateDetail(detail.getText());
        if (detailValidation != null) return detailValidation;

        String countryValidation = ProjectValidator.validateCountry(country.getText());
        if (countryValidation != null) return countryValidation;

        String categoryValidation = ProjectValidator.validateCategory(category.getValue());
        if (categoryValidation != null) return categoryValidation;

        return null;
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
        name.clear();
        members.clear();
        detail.clear();
        country.clear();
        category.setValue(null);
        teamLogoImageView.setImage(null);
        logoPath = null;
    }
}
