package com.example.cw_1601_sem2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectUtilsTest {

    private static final String TEST_FILE_NAME = "test_project_details.txt";

    @BeforeEach
    public void setUp() throws IOException {
        // Ensure the test file is clean before each test
        Files.deleteIfExists(Paths.get(TEST_FILE_NAME));
        ProjectUtils.setFileName(TEST_FILE_NAME);
        ProjectUtils.saveProjectDetailsToFile(new ArrayList<>());

    }

    @Test
    public void testAddProject() throws IOException {
        Project project = new Project(1, "Test Project", new String[]{"Alice", "Bob"}, "Description", "Country", "Category1", "path/to/logo.png");
        Project actual =ProjectUtils.addProject(project);

        System.out.println("Expected: " + project.toString());
        System.out.println("Actual: " + actual.toString());

        Assertions.assertEquals(project, actual);
    }
    @Test
    public void testSaveProjectDetailsToFile() throws IOException {
        // Prepare data
        Project project = new Project(1, "Alpha", new String[]{"Alice", "Bob"}, "Innovative AI project", "China", "AI", "C:\\path\\to\\logo.jpg");
        ProjectUtils.saveProjectDetailsToFile(List.of(project));

        // Verify the content of the file
        List<String> lines = Files.readAllLines(Paths.get(TEST_FILE_NAME));
        assertTrue(lines.contains("Category: AI"));
        assertTrue(lines.contains("id: 1, name: Alpha, team members: Alice,Bob, description: Innovative AI project, country: China, logoPath: C:\\path\\to\\logo.jpg"));
    }




    @Test
    public void testUpdateProject() throws IOException {
        // Prepare initial data
        Project initialProject = new Project(1, "Alpha", new String[]{"Alice"}, "Initial description", "China", "AI", "C:\\path\\to\\logo.jpg");
        ProjectUtils.addProject(initialProject);

        // Update project
        Project updatedProject = new Project(1, "Alpha Updated", new String[]{"Alice", "Bob"}, "Updated description", "China", "AI", "C:\\path\\to\\new_logo.jpg");
        ProjectUtils.updateProject(updatedProject);

        // Verify the project is updated
        List<String> lines = Files.readAllLines(Paths.get(TEST_FILE_NAME));
        assertTrue(lines.contains("id: 1, name: Alpha Updated, team members: Alice,Bob, description: Updated description, country: China, logoPath: C:\\path\\to\\new_logo.jpg"));
    }

    @Test
    public void testDeleteProjectById() throws IOException {
        Project project = new Project(1, "Test Project", new String[]{"Alice", "Bob"}, "Description", "Country", "Category1", "path/to/logo.png");
        ProjectUtils.addProject(project);

        ProjectUtils.deleteProjectById(1);

        Project projectFromFile = ProjectUtils.getProjectById(1);
        assertNull(projectFromFile);
    }

    @Test
    public void testIsIdExist() throws IOException {
        Project project = new Project(1, "Test Project", new String[]{"Alice", "Bob"}, "Description", "Country", "Category1", "path/to/logo.png");
        ProjectUtils.addProject(project);

        assertTrue(ProjectUtils.isIdExist(1));
        assertFalse(ProjectUtils.isIdExist(2));
    }

    @Test
    public void testLoadProjects() throws IOException {
        // Prepare test data
        String projectData = "Category: AI\nid: 1, name: Alpha, team members: Alice,Bob, description: Innovative AI project, country: China, logoPath: C:\\path\\to\\logo.jpg\n";
        Files.write(Paths.get(TEST_FILE_NAME), projectData.getBytes());

        // Load projects and assert
       List<Project> projects = ProjectUtils.loadProjects();
       assertEquals(1, projects.size());
       assertEquals("Alpha", projects.get(0).getName());
    }

    @Test
    public void testGetProjectById() throws IOException {
        Project project=new Project(23,"Shaithra",new String[]{"Alice","Bob"},"Description","Country","Category1", "path/to/logo.png");
        ProjectUtils.addProject(project);
        Project projectFromFile = ProjectUtils.getProjectById(23);
        assertEquals("Shaithra",projectFromFile.getName());
    }




}
