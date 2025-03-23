package com.example.cw_1601_sem2;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ViewProjectDetails {

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

    @FXML
    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        members.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTeamMembersFormatted()));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        country.setCellValueFactory(new PropertyValueFactory<>("country"));
        logo.setCellValueFactory(new PropertyValueFactory<>("logoImage"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));

        logo.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Project, ImageView> call(TableColumn<Project, ImageView> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(ImageView item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            setGraphic(item);
                        }
                    }
                };
            }
        });

        ObservableList<Project> projectList = loadProjectsFromFile();
        if (projectList != null) {
            bubbleSort(projectList); // Sort in place
            table.setItems(projectList); // Set the same list
        } else {
            System.out.println("No projects loaded.");
        }
    }

    private ObservableList<Project> loadProjectsFromFile() {
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
        return FXCollections.observableArrayList(projects);
    }

    private void bubbleSort(ObservableList<Project> projectList) {
        int n = projectList.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (projectList.get(j).getId() > projectList.get(j + 1).getId()) {
                    Project temp = projectList.get(j);
                    projectList.set(j, projectList.get(j + 1));
                    projectList.set(j + 1, temp);
                }
            }
        }
    }
}