package com.example.todolist.ui;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.Objects;

/**
 * @brief A custom notification alert window for displaying messages.
 * This class extends the JavaFX `Stage` class to create a modal alert window
 * that displays a title and a message. The window automatically closes after
 * a specified duration
 *
 * @see javafx.stage.Stage For the base class used to create the window.
 * @see javafx.scene.Scene For the scene graph used to display the content.
 * @see javafx.animation.PauseTransition For the auto-close functionality.
 *
 * @author Izemmouren Ilyes
 */
public class NotificationAlert extends Stage {

    /**
     * @brief Constructs a new NotificationAlert with the specified title and message.
     * This constructor initializes the alert window with the given title and message.
     * It sets up the window style, size, and content, and applies custom CSS for styling.
     * The window is set to auto-close after 1 second.
     *
     * @param title The title of the alert window.
     * @param message The message to display in the alert window.
     *
     * @note The alert window is modal, meaning it blocks interaction with other windows
     *       until it is closed. It uses a simple utility window style (StageStyle.UTILITY).
     */
    public NotificationAlert(String title, String message) {
        // Set up the stage
        initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows
        initStyle(StageStyle.UTILITY); // Simple window style
        setTitle(title);
        setWidth(400);
        setHeight(200);

        // Create the content
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", 18));
        titleLabel.setStyle("-fx-font-weight: bold;");

        Label messageLabel = new Label(message);
        messageLabel.setFont(Font.font("Arial", 14));

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(titleLabel, messageLabel);

        // Apply custom CSS
        layout.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/todolist/view/css/NotificationAlert.css")).toExternalForm());

        // Set the scene
        Scene scene = new Scene(layout);
        setScene(scene);

        // Auto-close after 1 second
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> close());
        pause.play();
    }

    /**
     * @brief Displays a notification alert with the specified title and message.
     * This static method creates and shows a new `NotificationAlert` window with the
     * given title and message. It provides a convenient way to display alerts without
     * explicitly creating an instance of the class.
     *
     * @param title The title of the alert window.
     * @param message The message to display in the alert window.
     *
     * @note This method is a shortcut for creating and showing a `NotificationAlert`.
     */
    public static void showAlert(String title, String message) {
        NotificationAlert alert = new NotificationAlert(title, message);
        alert.show();
    }
}