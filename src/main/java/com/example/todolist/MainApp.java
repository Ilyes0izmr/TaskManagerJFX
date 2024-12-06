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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/fxml/LogInView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Set up the stage (main window)
        primaryStage.setTitle("To-Do List Application");
        primaryStage.setScene(scene);

        //Optional: set initial window size (adjust as needed)
        primaryStage.setWidth(1550);   // Set the width of the window
        primaryStage.setHeight(850);  // Set the height of the window

        // Optional: make the window resizable or not
        primaryStage.setResizable(true);

        // Show the window
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(); // Launch the JavaFX application
    }
}
