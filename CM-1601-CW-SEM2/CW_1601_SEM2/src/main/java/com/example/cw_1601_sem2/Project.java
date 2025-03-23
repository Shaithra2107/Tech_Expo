package com.example.cw_1601_sem2;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Project {
    private int id;
    private String name;
    private String[] teamMembers;
    private String description;
    private String country;
    private String category;
    private String logoPath;
    private int points;

    // Constructor
    public Project(int id, String name, String[] teamMembers, String description, String country, String category, String logoPath) {
        this.id = id;
        this.name = name;
        this.teamMembers = teamMembers;
        this.description = description;
        this.country = country;
        this.category = category;
        this.logoPath = logoPath;
        this.points = 0;
    }

    // Getter and setter for fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(String[] teamMembers) {
        this.teamMembers = teamMembers;
    }

    // This method returns a formatted string of team members
    public String getTeamMembersFormatted() {
        return String.join(", ", teamMembers);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    public ImageView getLogoImage() {
        if (logoPath != null && !logoPath.isEmpty()) {
            File file = new File(logoPath);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
                imageView.setPreserveRatio(true);
                return imageView;
            }
        }
        return new ImageView(); // Return an empty ImageView if no logo
    }


    @Override
    public String toString() {
        return String.format("id: %d, name: %s, team members: %s, description: %s, country: %s, logoPath: %s",
                id, name, String.join(",", teamMembers), description, country, logoPath);
    }


}





