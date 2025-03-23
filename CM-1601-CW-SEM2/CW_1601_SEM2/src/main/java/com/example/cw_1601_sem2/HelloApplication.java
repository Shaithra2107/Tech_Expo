package com.example.cw_1601_sem2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main_Layout.fxml"));
        Scene scene = new Scene(root, 900, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        Image icon = new Image(getClass().getResourceAsStream("/com/example/cw_1601_sem2/image.png"));
        primaryStage.getIcons().add(icon);

        primaryStage.setTitle("Tech Expo");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}