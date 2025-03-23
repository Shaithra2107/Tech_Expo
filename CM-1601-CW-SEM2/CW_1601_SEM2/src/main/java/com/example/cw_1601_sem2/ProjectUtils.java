package com.example.cw_1601_sem2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProjectUtils {
    private static String FILE_NAME = "project_details.txt"; // Name of the file where project details are stored

    public static String getFileName() {
        return FILE_NAME;
    }

    public static void setFileName(String fileName) {
        FILE_NAME = fileName;
    }

    // Method to load projects from file
    public static List<Project> loadProjects() {
        List<Project> projects = new ArrayList<>();
        Path path = Paths.get(FILE_NAME);

        if (Files.exists(path)) {
            try {
                List<String> lines = Files.readAllLines(path);
                String category = null;
                for (String line : lines) {
                    if (line.startsWith("Category: ")) {
                        category = line.substring("Category: ".length()).trim();
                    } else if (line.startsWith("id: ")) {
                        Project project = parseProject(line, category);
                        if (project != null) {
                            projects.add(project);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not found: " + path.toAbsolutePath());
        }

        return projects;
    }

    private static Project parseProject(String line, String category) {
        try {
            String[] parts = line.split(", ");
            int id = Integer.parseInt(parts[0].split(": ")[1]);
            String name = parts[1].split(": ")[1];
            String[] teamMembers = parts[2].split(": ")[1].split(",");
            String description = parts[3].split(": ")[1];
            String country = parts[4].split(": ")[1];
            String logoPath = parts[5].split(": ")[1];

            return new Project(id, name, teamMembers, description, country, category, logoPath);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    // Method to save updated project details to file
    public static void saveProjectDetailsToFile(List<Project> projects) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            // Collect unique categories
            Set<String> categories = projects.stream()
                    .map(Project::getCategory)
                    .collect(Collectors.toSet());

            // Write each category and its projects to the file
            for (String category : categories) {
                writer.write("Category: " + category + "\n");

                for (Project project : projects) {
                    if (project.getCategory().equals(category)) {
                        writer.write(project.toString() + "\n");
                    }
                }
            }
            System.out.println("Project details saved in file successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Method to add a new project
    public static Project addProject(Project newProject) throws IOException {
        ensureFileExists();
        List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));
        List<String> updatedLines = new ArrayList<>();
        boolean categoryFound = false;
        boolean addedUnderCategory = false;

        for (String line : lines) {
            if (line.startsWith("Category: ")) {
                String category = line.substring("Category: ".length()).trim();
                if (category.equals(newProject.getCategory())) {
                    categoryFound = true;
                } else {
                    categoryFound = false;
                }
            }
            updatedLines.add(line);

            if (categoryFound && !addedUnderCategory && (line.startsWith("Category: ") || line.isBlank())) {
                updatedLines.add(newProject.toString());
                addedUnderCategory = true;
            }
        }

        if (!addedUnderCategory) {
            updatedLines.add("Category: " + newProject.getCategory());
            updatedLines.add(newProject.toString());
        }

        Files.write(Paths.get(FILE_NAME), updatedLines);
        return newProject;
    }

    // Method to update an existing project
    public static void updateProject(Project updatedProject) throws IOException {
        ensureFileExists();
        List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));
        List<String> updatedLines = new ArrayList<>();
        boolean projectUpdated = false;
        String currentCategory = "";

        for (String line : lines) {
            if (line.startsWith("Category:")) {
                currentCategory = line.split(": ")[1];
                updatedLines.add(line);
            } else if (!line.isBlank()) {
                if (line.startsWith("id: ")) {
                    String[] details = line.split(", ");
                    int existingId = Integer.parseInt(details[0].substring(4));
                    if (existingId == updatedProject.getId()) {
                        if (!currentCategory.equals(updatedProject.getCategory())) {
                            continue;
                        } else {
                            updatedLines.add(updatedProject.toString());
                            projectUpdated = true;
                        }
                    } else {
                        updatedLines.add(line);
                    }
                } else {
                    updatedLines.add(line);
                }
            } else {
                updatedLines.add(line);
            }
        }

        if (!projectUpdated) {
            updatedLines.add("Category: " + updatedProject.getCategory());
            updatedLines.add(updatedProject.toString());
        }

        Files.write(Paths.get(FILE_NAME), updatedLines);
    }

    // Method to delete a project by ID
    public static void deleteProjectById(int id) throws IOException {
        ensureFileExists();
        List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));
        List<String> updatedLines = new ArrayList<>();
        boolean projectDeleted = false;

        for (String line : lines) {
            if (line.startsWith("id: ")) {
                String[] details = line.split(", ");
                int existingId = Integer.parseInt(details[0].substring(4));
                if (existingId == id) {
                    projectDeleted = true;
                    continue;
                }
            }
            updatedLines.add(line);
        }

        if (projectDeleted) {
            Files.write(Paths.get(FILE_NAME), updatedLines);
        }
    }

    // Method to check if a project ID exists
    public static boolean isIdExist(int id) throws IOException {
        List<Project> projects = loadProjects();
        for (Project project : projects) {
            if (project.getId() == id) {
                return true;
            }
        }
        return false;
    }

    // Method to get a project by ID
    public static Project getProjectById(int id) throws IOException {
        List<Project> projects = loadProjects();
        for (Project project : projects) {
            if (project.getId() == id) {
                return project;
            }
        }
        return null;
    }

    // Method to ensure the file exists
    private static void ensureFileExists() throws IOException {
        Path path = Paths.get(FILE_NAME);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
    }



}
