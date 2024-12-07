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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/todolist/view/fxml/LogInView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Set up the stage (main window)
        primaryStage.setTitle("To-Do List Application - Sign Up");
        primaryStage.setScene(scene);

        // Optional: make the window resizable or not
        primaryStage.setResizable(true);
        //primaryStage.setFullScreen(false);
        primaryStage.setMaxHeight(850);
        primaryStage.setMaxWidth(1550);


        // Show the window
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(); // Launch the JavaFX application
    }
}