package com.example.cw_1601_sem2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RecordAwardsAndRecognitionsTest {

    @Test
    public void testValidateRatingInput_Valid1Star() {
        String result = ProjectValidator.validateRatingInput("*");
        assertEquals(null, result);
    }

    @Test
    public void testValidateRatingInput_Valid3Stars() {
        String result = ProjectValidator.validateRatingInput("***");
        assertEquals(null, result);
    }

    @Test
    public void testValidateRatingInput_Valid5Stars() {
        String result = ProjectValidator.validateRatingInput("*****");
        assertEquals(null, result);
    }

    @Test
    public void testValidateRatingInput_Empty() {
        String result = ProjectValidator.validateRatingInput("");
        assertEquals("Rating cannot be empty!", result);
    }

    @Test
    public void testValidateRatingInput_Null() {
        String result = ProjectValidator.validateRatingInput(null);
        assertEquals("Rating cannot be empty!", result);
    }

    @Test
    public void testValidateRatingInput_MoreThan5Stars() {
        String result = ProjectValidator.validateRatingInput("******");
        assertEquals("Rating must be between 1 and 5 stars (e.g., '*', '**', '***', '****', '*****').", result);
    }

    @Test
    public void testValidateRatingInput_NonStarCharacters() {
        String result = ProjectValidator.validateRatingInput("****a");
        assertEquals("Rating must be between 1 and 5 stars (e.g., '*', '**', '***', '****', '*****').", result);
    }

    @Test
    public void testValidateRatingInput_LessThan1Star() {
        String result = ProjectValidator.validateRatingInput("");
        assertEquals("Rating cannot be empty!", result);
    }

}