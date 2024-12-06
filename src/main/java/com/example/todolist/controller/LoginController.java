package com.example.todolist.controller;

import com.example.todolist.dao.UserDAO;
import com.example.todolist.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (userDAO.login(email, password)) {
            showAlert("Success", "Login successful!", Alert.AlertType.INFORMATION);
            try {
                // Ensure the correct path to your FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/todolist/view/fxml/HomeView.fxml"));
                Parent root = loader.load();

                // Get current stage and set new scene
                Stage stage = (Stage) emailField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Home Page - To-Do List Application");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to load Home page.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Error", "Invalid email or password.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleSignUp() {
        try {
            // Switch to the Sign-Up view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/todolist/view/fxml/SignUpView.fxml"));
            Parent root = loader.load();

            // Get current stage and set new scene
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Sign Up - To-Do List Application");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load Sign-Up page.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
