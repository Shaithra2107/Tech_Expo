package com.example.cw_1601_sem2;

public class ProjectValidator {

    public static String validateId(String idText) {
        if (idText == null || idText.trim().isEmpty()) {
            return "Project ID cannot be empty!";
        }
        try {
            Integer.parseInt(idText.trim());
        } catch (NumberFormatException e) {
            return "Invalid Project ID format!";
        }
        return null;
    }

    public static String validateName(String nameText) {
        if (nameText == null || nameText.trim().isEmpty()) {
            return "Project name cannot be empty!";
        }
        return null;
    }

    public static String validateMembers(String membersText) {
        if (membersText == null || membersText.trim().isEmpty()) {
            return "Team members cannot be empty!";
        }
        return null;
    }

    public static String validateDetail(String detailText) {
        if (detailText == null || detailText.trim().isEmpty()) {
            return "Project details cannot be empty!";
        }
        return null;
    }

    public static String validateCountry(String countryText) {
        if (countryText == null || countryText.trim().isEmpty()) {
            return "Country cannot be empty!";
        }
        return null;
    }

    public static String validateCategory(String categoryValue) {
        if (categoryValue == null) {
            return "Category must be selected!";
        }
        return null;
    }

    // Validation for Delete Projects

    public static String validateDeleteProjectId(String idText) {
        if (idText == null || idText.trim().isEmpty()) {
            return "Project ID cannot be empty!";
        }
        try {
            Integer.parseInt(idText.trim());
        } catch (NumberFormatException e) {
            return "Invalid Project ID format!";
        }
        return null;
    }
    public static String validateUserValidation(String userValidationText) {
        if (userValidationText == null || userValidationText.trim().isEmpty()) {
            return "Confirmation text cannot be empty!";
        }
        if (!userValidationText.trim().equalsIgnoreCase("yes")) {
            return "User cancelled the the deletion process!";
        }
        return null;
    }

    // Validation for rating input
    public static String validateRatingInput(String stars) {
        if (stars == null || stars.trim().isEmpty()) {
            return "Rating cannot be empty!";
        }
        if (stars.matches("[*]{1,5}")) {
            return null; // Valid star rating
        }
        return "Rating must be between 1 and 5 stars (e.g., '*', '**', '***', '****', '*****').";
    }








}
