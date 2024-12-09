package com.example.todolist.controller;

import com.example.todolist.dao.UserDAO;
import com.example.todolist.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField userNameField;

    @FXML
    private TextField fullNameField;

    @FXML
    private Button signUpButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField passwordFieldConfirmation;

    private final UserDAO userDAO;

    public SignUpController() {
        this.userDAO = new UserDAO();
    }

    @FXML
    private void handleSignUp() {
        String email = emailField.getText();
        String userName = userNameField.getText();
        String fullName = fullNameField.getText();
        String password = passwordField.getText();
        String confirmPassword = passwordFieldConfirmation.getText();

        // Validation checks
        if (email == null || email.isEmpty() || !isValidEmail(email)) {
            showAlert("Invalid Email", "Please enter a valid email containing '@'.");
            return;
        }

        if (userName == null || userName.isEmpty() || !isValidUserName(userName)) {
            showAlert("Invalid User Name", "User name must not contain spaces.");
            return;
        }

        if (fullName == null || fullName.isEmpty() || !isValidFullName(fullName)) {
            showAlert("Invalid Full Name", "Full name must not contain numbers.");
            return;
        }

        if (password == null || password.isEmpty() || !isValidPassword(password)) {
            showAlert("Invalid Password", "Password must contain at least one uppercase letter, one lowercase letter, and one number.");
            return;
        }

        if (!isMatchConfirmationPassword(password, confirmPassword)) {
            showAlert("Password Mismatch", "Passwords do not match.");
            return;
        }

        // Register new user
        User newUser = new User(userName, fullName, password, email);
        boolean isRegistered = userDAO.signUp(newUser);

        if (isRegistered) {
            try {
                // Load HomeView.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/todolist/view/fxml/HomeView.fxml"));
                Parent root = loader.load();

                // Switch scene
                Stage stage = (Stage) userNameField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Home Page - To-Do List Application");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to load Home page.");
            }
        } else {
            showAlert("Error", "Registration failed. Please try again.");
        }
    }

    @FXML
    private void handleLogIn() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/todolist/view/fxml/LoginView.fxml"));
            Parent root = loader.load();
            // Switch to Login view
            Stage stage = (Stage) userNameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login - To-Do List Application");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load Login page.");
        }
    }

    // Validation methods
    private boolean isValidEmail(String email) {
        return email.contains("@");
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");
    }

    private boolean isMatchConfirmationPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    private boolean isValidUserName(String userName) {
        return !userName.contains(" ");
    }

    private boolean isValidFullName(String fullName) {
        return fullName != null && !fullName.matches(".*\\d.*");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
