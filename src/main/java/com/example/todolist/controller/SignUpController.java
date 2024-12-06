package com.example.todolist.controller;

import com.example.todolist.dao.UserDAO;
import com.example.todolist.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

        if (!isValidEmail(email)) {
            showAlert("Invalid Email", "Email must contain '@'.");
            return;
        }

        if (!isValidPassword(password)) {
            showAlert("Invalid Password", "Password must contain at least one uppercase letter, one lowercase letter, and one number.");
            return;
        }

        if (!isValidUserName(userName)) {
            showAlert("Invalid User Name", "User name must not contain white spaces.");
            return;
        }

        if (!isMatchConfirmationPassword(password, confirmPassword)) {
            showAlert("Invalid Password", "Password does not match.");
            return;
        }

        User newUser = new User(userName, fullName, password, email);
        boolean isRegistered = userDAO.signUp(newUser);

        if (isRegistered) {
            showAlert("Success", "User registered successfully.");
        } else {
            showAlert("Error", "Registration failed.");
        }
    }

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
        return !fullName.matches(".*\\d.*");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}