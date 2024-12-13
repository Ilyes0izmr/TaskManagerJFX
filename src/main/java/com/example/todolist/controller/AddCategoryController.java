package com.example.todolist.controller;

import com.example.todolist.dao.CategoryDAO;
import com.example.todolist.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCategoryController {

    @FXML
    private TextField categoryNameField;

    // Handles adding the new category
    @FXML
    private void handleAddCategory() {
        String categoryName = categoryNameField.getText().trim();
        if (!categoryName.isEmpty()) {
            // Call the DAO to add the category to the database
            CategoryDAO categoryDAO = new CategoryDAO();
            boolean isAdded = categoryDAO.addCategory(categoryName, User.getUserName());
            if (isAdded) {
                // Close the popup after successful addition
                Stage stage = (Stage) categoryNameField.getScene().getWindow();
                stage.close();
            } else {
                // Show error message (optional)
                System.out.println("Failed to add category. Please try again.");
            }
        }
    }
}
