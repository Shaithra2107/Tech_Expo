// File: AddingProjectsTest.java
package com.example.cw_1601_sem2;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class AddingProjectsTest {


    @Test
    public void addTest() throws IOException {
        Project newProject = new Project(1, "Software Engineering", new String[]{"John", "Jane"}, "description", "SE", "AI", null);
        ProjectUtils.addProject(newProject);

        // Debugging output
        System.out.println("Added project: " + newProject);
        System.out.println("Does ID 1 exist? " + ProjectUtils.isIdExist(1));

        assertTrue(ProjectUtils.isIdExist(1));
    }

    @Test
    public void testValidateId() {
        assertEquals("Project ID cannot be empty!", ProjectValidator.validateId(" "));
        assertEquals("Invalid Project ID format!", ProjectValidator.validateId("abc"));
        assertEquals(null, ProjectValidator.validateId("123"));
    }

    @Test
    public void testValidateName() {
        assertEquals("Project name cannot be empty!", ProjectValidator.validateName(" "));
        assertEquals(null, ProjectValidator.validateName("Project X"));
    }

    @Test
    public void testValidateMembers() {
        assertEquals("Team members cannot be empty!", ProjectValidator.validateMembers(""));
        assertNull(ProjectValidator.validateMembers("John, Jane"));
    }

    @Test
    public void testValidateDetail() {
        assertEquals("Project details cannot be empty!", ProjectValidator.validateDetail(" "));
        assertEquals(null, ProjectValidator.validateDetail("This is a detailed description."));
    }

    @Test
    public void testValidateCountry() {
        assertEquals("Country cannot be empty!", ProjectValidator.validateCountry(" "));
        assertEquals(null, ProjectValidator.validateCountry("USA"));
    }

    @Test
    public void testValidateCategory() {
        assertEquals("Category must be selected!", ProjectValidator.validateCategory(null));
        assertEquals(null, ProjectValidator.validateCategory("AI"));
    }


}
