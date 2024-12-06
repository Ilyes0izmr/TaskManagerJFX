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
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (userDAO.login(username, password)) {
            showAlert("Success", "Login successful!", Alert.AlertType.INFORMATION);

        } else {
            showAlert("Error", "Invalid username or password.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleSignUp() {
        try {
            // Switch to the Sign Up view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/todolist/view/fxml/SignUpView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();  // Reuse the same stage
            stage.setScene(new Scene(root));
            stage.setTitle("Sign Up - To-Do List Application");
        } catch (IOException e) {
            e.printStackTrace();  // Handle the exception if loading fails
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
