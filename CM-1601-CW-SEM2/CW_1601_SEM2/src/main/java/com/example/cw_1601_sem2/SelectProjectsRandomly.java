package com.example.cw_1601_sem2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class SelectProjectsRandomly {

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
    private TableColumn<Project, ImageView> logo;

    @FXML
    private TableColumn<Project, String> category;

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

    }

    @FXML
    public void select(ActionEvent event) {
        List<Project> projects = readProjectsFromFile();

        Map<String, Project> randomlySelectedProjects = new HashMap<>();

        Map<String, List<Project>> projectsByCategory = projects.stream()
                .collect(Collectors.groupingBy(Project::getCategory));

        Random random = new Random();
        for (String category : projectsByCategory.keySet()) {
            List<Project> projectsInCategory = projectsByCategory.get(category);
            int randomIndex = random.nextInt(projectsInCategory.size());
            Project selectedProject = projectsInCategory.get(randomIndex);
            randomlySelectedProjects.put(category, selectedProject);
        }

        selectedProjects = new ArrayList<>(randomlySelectedProjects.values());

        ObservableList<Project> selectedProjectsList = FXCollections.observableArrayList(selectedProjects);
        table.setItems(selectedProjectsList);
    }

    private List<Project> readProjectsFromFile() {
        List<Project> projects = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get("project_details.txt"));
            String currentCategory = "";
            for (String line : lines) {
                if (line.startsWith("Category: ")) {
                    currentCategory = line.substring(10).trim();
                    continue;
                }
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] details = line.split(", ");
                if (details.length < 6) continue; // Ensure there are enough fields
                try {
                    int id = Integer.parseInt(details[0].substring(4).trim());
                    String name = details[1].substring(6).trim();
                    String[] teamMembers = details[2].substring(13).trim().split("\\s*,\\s*");
                    String description = details[3].substring(13).trim();
                    String country = details[4].substring(8).trim();
                    String logoPath = details[5].substring(10).trim();

                    // Create an ImageView from the logoPath
                    ImageView logoImage = new ImageView(new Image("file:" + logoPath));

                    projects.add(new Project(id, name, teamMembers, description, country, currentCategory, logoPath));
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format in line: " + line);
                } catch (StringIndexOutOfBoundsException e) {
                    System.err.println("Error parsing line: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return projects;
    }

    public static List<Project> getSelectedProjects() {
        return selectedProjects;
    }
}
