package com.example.cw_1601_sem2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecordAwardsAndRecognitions {

    @FXML
    private TableView<Project> table;

    @FXML
    private TableColumn<Project, Integer> id;

    @FXML
    private TableColumn<Project, String> name;

    @FXML
    private TableColumn<Project, String> members;

    @FXML
    private TableColumn<Project, String> description;

    @FXML
    private TableColumn<Project, String> country;

    @FXML
    private TableColumn<Project, String> logo;

    @FXML
    private TableColumn<Project, String> category;

    @FXML
    private TableColumn<Project, Integer> points;

    private static List<Project> selectedProjects = new ArrayList<>();

    @FXML
    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        members.setCellValueFactory(cellData -> {
            // Convert the array of team members into a comma-separated string
            return new javafx.beans.property.SimpleStringProperty(String.join(", ", cellData.getValue().getTeamMembers()));
        });
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        country.setCellValueFactory(new PropertyValueFactory<>("country"));

        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        points.setCellValueFactory(new PropertyValueFactory<>("points"));

        ObservableList<Project> emptyList = FXCollections.observableArrayList();
        table.setItems(emptyList);
    }

    public void setSelectedProjects(List<Project> selectedProjects) {
        this.selectedProjects = selectedProjects;
        displaySelectedProjects();
    }

    private void displaySelectedProjects() {
        ObservableList<Project> selectedProjectsList = FXCollections.observableArrayList(selectedProjects);
        table.setItems(selectedProjectsList);
    }

    @FXML
    private void rateProjects(ActionEvent event) {
        for (Project project : selectedProjects) {
            int totalPoints = 0;
            for (int judge = 1; judge <= 4; judge++) {
                int rating = promptForRating(project, judge);
                totalPoints += rating;
            }
            project.setPoints(totalPoints);
        }

        bubbleSortProjects(selectedProjects); // Sort projects by points
        table.refresh();
        displayTopThreeProjects();
    }

    private int promptForRating(Project project, int judgeNumber) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Rate Project " + project.getName());
        dialog.setHeaderText("Judge " + judgeNumber + ": Enter rating (1-5 stars) for " + project.getName());
        dialog.setContentText("Rating:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String stars = result.get();
            String validationMessage = ProjectValidator.validateRatingInput(stars);
            if (validationMessage == null) {
                return stars.length(); // Convert stars to numeric rating
            } else {
                showAlert("Invalid Rating", validationMessage);
                return promptForRating(project, judgeNumber);
            }
        }
        return 0; // Default rating if dialog is canceled
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void bubbleSortProjects(List<Project> projects) {
        int n = projects.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (projects.get(j).getPoints() < projects.get(j + 1).getPoints()) {
                    // Swap projects[j] with projects[j + 1]
                    Project temp = projects.get(j);
                    projects.set(j, projects.get(j + 1));
                    projects.set(j + 1, temp);
                }
            }
        }
    }

    static List<Project> displayTopThreeProjects() {
        List<Project> topThreeProjects = selectedProjects.size() > 3 ? selectedProjects.subList(0, 3) : new ArrayList<>(selectedProjects);
        // Debug print statement
        System.out.println("Top three projects:");
        for (Project project : topThreeProjects) {
            System.out.println(project.getName() + " with " + project.getPoints() + " points.");
        }
        return topThreeProjects;
    }
}
