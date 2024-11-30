package com.example.todolist;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the FXML file for the login view
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/fxml/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Set up the stage (main window)
        primaryStage.setTitle("To-Do List Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(); // Launch the JavaFX application
    }
}
