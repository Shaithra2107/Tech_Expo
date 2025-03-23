package com.example.cw_1601_sem2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteProjectValidatorTest {

    @Test
    public void testValidateId() {
        assertEquals("Project ID cannot be empty!", ProjectValidator.validateDeleteProjectId(" "));
        assertEquals("Invalid Project ID format!", ProjectValidator.validateDeleteProjectId("abc"));
        assertEquals(null, ProjectValidator.validateDeleteProjectId("123"));
    }

    @Test
    public void testValidateUserValidation() {
        assertEquals("Confirmation text cannot be empty!", ProjectValidator.validateUserValidation(" "));
        assertEquals("User cancelled the the deletion process!", ProjectValidator.validateUserValidation("no"));
        assertEquals(null, ProjectValidator.validateUserValidation("yes"));
    }
}
