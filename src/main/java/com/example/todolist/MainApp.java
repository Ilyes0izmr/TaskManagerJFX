package com.example.todolist;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the FXML file for the sign-up view
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/todolist/view/fxml/SignUpView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Set up the stage (main window)
        primaryStage.setTitle("To-Do List Application - Sign Up");
        primaryStage.setScene(scene);

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