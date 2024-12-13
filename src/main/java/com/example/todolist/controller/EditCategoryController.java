package com.example.todolist.controller;

import com.example.todolist.model.Category;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import com.example.todolist.dao.CategoryDAO;

public class EditCategoryController {

    @FXML
    private TextField categoryNameField;

    private Category category; // Current category being edited
    private CategoryDAO categoryDAO;

    public void initialize() {
        categoryDAO = new CategoryDAO();
    }

    /**
     * Set the category to edit and populate fields with its data.
     *
     * @param category The category to edit
     */
    public void setCategory(Category category) {
        this.category = category;
        if (category != null) {
            categoryNameField.setText(category.getName());
        }
    }

    /**
     * Handle the Save Changes button click.
     * Updates the category name in the database.
     */
    @FXML
    private void onSaveChanges() {
        String newCategoryName = categoryNameField.getText();

        if (newCategoryName.isEmpty()) {
            showAlert("Error", "Category name must not be empty.");
            return;
        }

        if (category == null) {
            showAlert("Error", "No category selected to edit.");
            return;
        }

        boolean success = categoryDAO.rename(category.getName(), category.getUserName(), newCategoryName);
        if (success) {
            category.setName(newCategoryName); // Update the in-memory category object
            categoryNameField.getScene().getWindow().hide();
        } else {
            showAlert("Error", "Failed to update category. Ensure the new name is unique.");
        }
    }

    /**
     * Handle the Cancel button click.
     * Closes the window without saving changes.
     */
    @FXML
    private void onCancel() {
        categoryNameField.getScene().getWindow().hide();
    }


    /**
     * Show an alert dialog with the specified title and message.
     *
     * @param title   The title of the alert dialog
     * @param message The message to display
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
