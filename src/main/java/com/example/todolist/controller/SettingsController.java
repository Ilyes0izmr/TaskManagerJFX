package com.example.todolist.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsController {

    @FXML
    private AnchorPane contentPane; // Reference to the content pane in the FXML

    // Method to handle the "Back to Home" button click
    public void handleBackToHome(ActionEvent actionEvent) throws IOException {
        loadContent("/com/example/todolist/view/fxml/HomeView.fxml");
    }

    // Method to handle the "Dashboard" button click
    public void handleDashboard(ActionEvent actionEvent) {
        loadContent("dashboard.fxml");
    }

    // Method to handle the "Appearance" button click
    public void handleAppearance(ActionEvent actionEvent) {
        loadContent("appearance.fxml");
    }

    // Method to handle the "Notification" button click
    public void handleNotification(ActionEvent actionEvent) {
        loadContent("notification.fxml");
    }

    // Method to handle the "Logout" button click
    public void handleLogout(ActionEvent actionEvent) {
        loadContent("/com/example/todolist/view/fxml/LoginView.fxml");
        // Implement logout logic here (e.g., close the application or navigate to a login screen)
        System.out.println("/com/example/todolist/view/fxml/LoginView.fxml");
    }

    // Helper method to load FXML content into the contentPane
    private void loadContent(String fxmlFile) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent content = loader.load();
            // Clear the contentPane and add the new content
            contentPane.getChildren().clear();
            contentPane.getChildren().add(content);

            // Anchor the content to all sides of the contentPane
            AnchorPane.setTopAnchor(content, 0.0);
            AnchorPane.setBottomAnchor(content, 0.0);
            AnchorPane.setLeftAnchor(content, 0.0);
            AnchorPane.setRightAnchor(content, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading FXML file: " + fxmlFile);
        }
    }
}