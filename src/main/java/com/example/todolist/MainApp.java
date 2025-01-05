package com.example.todolist;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * @brief The MainApp class is the entry point for the To-Do List Application.
 * It initializes and launches the primary window of the application, specifically the login view.
 *
 * @author Izemmouren Ilyes
 */
public class MainApp extends Application {

    /**
     * The main entry point for the JavaFX application. Sets up and displays the primary window.
     *
     * @param primaryStage The primary stage (window) for the application.
     * @throws IOException If the FXML file cannot be loaded.
     *
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the FXML file for the sign-up view
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/todolist/view/fxml/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Set up the stage (main window)
        primaryStage.setTitle("To-Do List Application - Sign Up");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.setMaxHeight(850);
        primaryStage.setMaxWidth(1550);

        // Show the window
        primaryStage.show();
    }

    /**
     * The entry point for the Java application. Launches the JavaFX application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(); // Launch the JavaFX application
    }
}