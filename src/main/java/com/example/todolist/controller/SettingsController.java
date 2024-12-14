package com.example.todolist.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsController {

    @FXML
    private AnchorPane contentPane; // Reference to the content pane in the FXML

    @FXML
    private Button dashboardButton;

    @FXML
    private Button appearanceButton;

    @FXML
    private Button notificationButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button logoutButton;

    // Method to handle the "Dashboard" button click
    public void handleDashboard(ActionEvent actionEvent) {
        clearActiveButtonStyles();
        dashboardButton.getStyleClass().add("active-button"); // Highlight the dashboard button
        loadContent("/com/example/todolist/view/fxml/DashboardView.fxml");
    }

    // Method to handle the "Appearance" button click
    public void handleAppearance(ActionEvent actionEvent) {
        clearActiveButtonStyles();
        appearanceButton.getStyleClass().add("active-button"); // Highlight the appearance button
        loadContent("/com/example/todolist/view/fxml/AppearanceView.fxml");
    }

    // Method to handle the "Notification" button click
    public void handleNotification(ActionEvent actionEvent) {
        clearActiveButtonStyles();
        notificationButton.getStyleClass().add("active-button"); // Highlight the notification button
        loadContent("/com/example/todolist/view/fxml/NotificationView.fxml");
    }

    // Method to handle the "Back to Home" button click
    public void handleBackToHome(ActionEvent actionEvent) {
        // Navigate to the home screen (do not load into contentPane)
        navigateTo("/com/example/todolist/view/fxml/HomeView.fxml");
    }

    // Method to handle the "Logout" button click
    public void handleLogout(ActionEvent actionEvent) {
        // Navigate to the login screen (do not load into contentPane)
        navigateTo("/com/example/todolist/view/fxml/LoginView.fxml");
        System.out.println("Logged out");
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

    // Helper method to navigate to a new screen (not in contentPane)
    private void navigateTo(String fxmlFile) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) contentPane.getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error navigating to FXML file: " + fxmlFile);
        }
    }

    // Helper method to clear the active state from all buttons
    private void clearActiveButtonStyles() {
        dashboardButton.getStyleClass().remove("active-button");
        appearanceButton.getStyleClass().remove("active-button");
        notificationButton.getStyleClass().remove("active-button");
        homeButton.getStyleClass().remove("active-button");
        logoutButton.getStyleClass().remove("active-button");
    }
}