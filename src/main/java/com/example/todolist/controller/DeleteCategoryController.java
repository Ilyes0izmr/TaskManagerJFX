package com.example.todolist.controller;

import com.example.todolist.dao.CategoryDAO;
import com.example.todolist.model.Category;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DeleteCategoryController {

    @FXML
    private Button confirmButton;


    private Category categoryToDelete;
    private HomeController homeController;

    private final CategoryDAO categoryDAO = new CategoryDAO();

    /**
     * Set the category to be deleted.
     *
     * @param category The category to be deleted
     */
    public void setCategory(Category category) {
        this.categoryToDelete = category;
    }

    /**
     * Handle the Confirm button click.
     * This should trigger the deletion logic for the category and refresh the categories list.
     */
    @FXML
    public void handleConfirm() {
        if (categoryToDelete != null) {
            boolean success = categoryDAO.deleteCategory(categoryToDelete.getName());
            if (success) {
                System.out.println("Category '" + categoryToDelete.getName() + "' deleted successfully.");
                if (homeController != null) {
                    homeController.refreshCategories();
                }
            } else {
                System.out.println("Failed to delete category '" + categoryToDelete.getName() + "'.");
            }
        } else {
            System.out.println("No category provided for deletion.");
        }

        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Handle the Cancel button click.
     * This simply closes the pop-up window without taking any action.
     */
    @FXML
    public void handleCancel() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

}
