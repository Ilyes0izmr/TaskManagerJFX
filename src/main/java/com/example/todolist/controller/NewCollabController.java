package com.example.todolist.controller;

import com.example.todolist.dao.CategoryDAO;
import com.example.todolist.dao.CollabCategoryDAO;
import com.example.todolist.model.Category;
import com.example.todolist.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.util.ArrayList;

public class NewCollabController {
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private TextField usernameField;
    @FXML
    private CheckBox fullAccessCheckBox;

    public void initialize() {
        // Populate categories for the current user
        ObservableList<String> categories = FXCollections.observableArrayList();
        CategoryDAO categoryDAO = new CategoryDAO();

        ArrayList<Category> categoryList = categoryDAO.getAllCategories(User.getUserName());
        for (Category category : categoryList) {
            categories.add(category.getName());
        }
        categoryComboBox.setItems(categories);

        fullAccessCheckBox.setSelected(false);
    }

    @FXML
    private void handleAddCollaboration() {
        String selectedCategory = categoryComboBox.getValue();
        String collaborator = usernameField.getText().trim();
        boolean fullAccess = fullAccessCheckBox.isSelected();

        if (selectedCategory == null || collaborator.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill all fields.");
            return;
        }

        if (collaborator.equals(User.getUserName())) {
            showAlert(Alert.AlertType.ERROR, "Invalid Username", "You cannot collaborate with yourself.");
            return;
        }

        try {
            CollabCategoryDAO dao = new CollabCategoryDAO();
            boolean success = dao.addCollaboration(selectedCategory, User.getUserName(), collaborator, fullAccess);

            if (success) {
                usernameField.clear();
                categoryComboBox.setValue(null);
                fullAccessCheckBox.setSelected(false);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add collaboration.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
