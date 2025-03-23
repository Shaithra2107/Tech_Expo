package com.example.cw_1601_sem2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VisualizeWinningProjects implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private BarChart<Number, String> barChart;

    @FXML
    private CategoryAxis yAxis;

    @FXML
    private NumberAxis xAxis;

    private List<Project> topProjects;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        yAxis.setLabel("Projects");
        xAxis.setLabel("Points");
        topProjects=RecordAwardsAndRecognitions.displayTopThreeProjects();
        displayTopProjectsInChart();

    }



    public void displayTopProjectsInChart() {
        XYChart.Series<Number, String> series = new XYChart.Series<>();
        for (Project project : topProjects) {
            String projectLabel = project.getName() + ", " + project.getCountry();
            series.getData().add(new XYChart.Data<>(project.getPoints(), projectLabel));
        }
        barChart.getData().clear();
        barChart.getData().add(series);
    }
}
