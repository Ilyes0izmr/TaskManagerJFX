package com.example.todolist.controller;

import com.example.todolist.dao.UserDAO;
import com.example.todolist.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * TODO: you have to fix this controler ilyes later on
 */
public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    /*private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (userDAO.login(username, password)) {
            showAlert("Success", "Login successful!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Invalid username or password.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    /*private void handleSignUp() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = new User(username, password);
        if (userDAO.signUp(user)) {
            showAlert("Success", "Sign-up successful!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Sign-up failed. Try again.", Alert.AlertType.ERROR);
        }
    }*/

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
