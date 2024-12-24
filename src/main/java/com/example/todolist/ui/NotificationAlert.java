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

public class NotificationAlert extends Stage {

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
        layout.getStylesheets().add(getClass().getResource("/com/example/todolist/view/css/NotificationAlert.css").toExternalForm());

        // Set the scene
        Scene scene = new Scene(layout);
        setScene(scene);

        // Auto-close after 3 seconds
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> close());
        pause.play();
    }

    // Static method to show the alert
    public static void showAlert(String title, String message) {
        NotificationAlert alert = new NotificationAlert(title, message);
        alert.show();
    }
}
