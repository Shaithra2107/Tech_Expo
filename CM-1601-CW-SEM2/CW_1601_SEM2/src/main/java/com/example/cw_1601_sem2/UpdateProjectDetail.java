package com.example.cw_1601_sem2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class UpdateProjectDetail {
    @FXML
    private ImageView teamLogoImageView;
    @FXML
    private Text valid;
    @FXML
    private Label mylabel;
    @FXML
    private ChoiceBox<String> category;
    @FXML
    private Button logo_button;
    @FXML
    private TextField detail;
    @FXML
    private TextField members;
    @FXML
    private TextField country;
    @FXML
    private TextField name;
    @FXML
    private TextField id;

    private String logoPath; // Store the selected logo path
    private Project existingProject;

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
            teamLogoImageView.setImage(new javafx.scene.image.Image(selectedFile.toURI().toString()));
        }
    }

    @FXML
    protected void save(ActionEvent event) throws IOException {
        try {
            int projectId = Integer.parseInt(id.getText());

            // Ensure existingProject is set and the ID matches the input ID
            if (existingProject == null || existingProject.getId() != projectId) {
                showAlert(Alert.AlertType.ERROR, "Error", "Project ID does not exist. Please check the Project ID!");
                return;
            }

            // Update project details
            String projectName = name.getText();
            String projectMembers = members.getText();
            String projectDescription = detail.getText();
            String projectCountry = country.getText();
            String projectCategory = category.getValue();
            // Ensure logoPath is set correctly
            String updatedLogoPath = (logoPath != null) ? logoPath : existingProject.getLogoPath();

            // Convert projectMembers to String[]
            String[] projectMembersArray = projectMembers.split(",\\s*");

            // Create the updated project instance
            Project updatedProject = new Project(
                    projectId,
                    projectName,
                    projectMembersArray, // Pass the String[] array
                    projectDescription,
                    projectCountry,
                    projectCategory,
                    updatedLogoPath
            );

            // Load existing projects from file
            List<Project> existingProjects = ProjectUtils.loadProjects();

            // Update the specific project
            boolean projectFound = false;
            for (int i = 0; i < existingProjects.size(); i++) {
                if (existingProjects.get(i).getId() == projectId) {
                    existingProjects.set(i, updatedProject);
                    projectFound = true;
                    break;
                }
            }

            if (!projectFound) {
                showAlert(Alert.AlertType.ERROR, "Error", "Project not found!");
                return;
            }

            // Save updated projects back to file
            ProjectUtils.saveProjectDetailsToFile(existingProjects);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Project details updated successfully!");

            clearFields();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid Project ID (numeric)!");
        }
    }

    @FXML
    protected void Check(ActionEvent actionEvent) {
        // Verify user
        if (!UserVerification.verifyUser()) {
            showAlert(Alert.AlertType.ERROR, "Error", "User verification failed. Cannot update the project.");
            clearFields();
            return; // Exit the method if user verification fails
        }
        try {
            int projectId = Integer.parseInt(id.getText());

            // Check if the project ID exists
            boolean idExists = ProjectUtils.isIdExist(projectId);

            if (idExists) {
                existingProject = ProjectUtils.getProjectById(projectId);
                if (existingProject != null) {
                    populateFields(existingProject);
                    showAlert(Alert.AlertType.INFORMATION, "Project ID Check", "Project ID exists in the text file.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Project ID exists in the file but cannot be loaded. Please try again.");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Project ID Check", "Project ID does not exist in the text file!");
                clearFields();
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid Project ID (numeric)!");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while checking the project ID!");
        }
    }

    private void populateFields(Project project) {
        name.setText(project.getName());
        // Convert team members array to a comma-separated string
        members.setText(String.join(", ", project.getTeamMembers()));
        detail.setText(project.getDescription());
        country.setText(project.getCountry());
        category.setValue(project.getCategory());
        if (project.getLogoPath() != null) {
            File file = new File(project.getLogoPath());
            teamLogoImageView.setImage(new javafx.scene.image.Image(file.toURI().toString()));
        }
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
        existingProject = null;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
