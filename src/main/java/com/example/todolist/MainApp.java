package com.example.todolist;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @brief The MainApp class is the entry point for the To-Do List Application.
 *
 * It initializes and launches the primary window of the application, specifically the login view.
 * This class sets up the stage, loads the FXML file, and configures the window properties.
 *
 * @author Izemmouren Ilyes
 */
public class MainApp extends Application {

    /**
     * @brief The main entry point for the JavaFX application.
     *
     * This method sets up and displays the primary window (stage) of the application.
     * It loads the FXML file for the login view and configures the stage properties.
     *
     * @param primaryStage The primary stage (window) for the application.
     * @throws IOException If the FXML file cannot be loaded.
     *
     * @note Ensure the FXML file path is correct and accessible.
     * @todo Consider adding a splash screen or loading animation while the application initializes.
     * @todo Add error handling for cases where the FXML file is missing or corrupted.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the FXML file for the login view
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/todolist/view/fxml/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Set up the stage (main window)
        primaryStage.setTitle("To-Do List Application - Login");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.setMaxHeight(850);
        primaryStage.setMaxWidth(1550);

        // Show the window
        primaryStage.show();
    }

    /**
     * @brief The entry point for the Java application.
     *
     * This method launches the JavaFX application by calling the `launch` method.
     *
     * @param args Command-line arguments passed to the application.
     *
     * @note Command-line arguments are not currently used in this application.
     * @todo Consider adding support for command-line arguments (e.g., to specify a configuration file).
     */
    public static void main(String[] args) {
        launch(); // Launch the JavaFX application
    }
}