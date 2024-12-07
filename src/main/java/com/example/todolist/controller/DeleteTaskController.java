package com.example.todolist.controller;

import com.example.todolist.dao.TaskDAO;
import com.example.todolist.model.TaskImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DeleteTaskController {

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    private TaskImpl taskToDelete;
    private HomeController homeController;

    /**
     * Set the task to be deleted.
     *
     * @param task The task to be deleted
     */
    public void setTask(TaskImpl task) {
        this.taskToDelete = task;
    }



    /**
     * Handle the Confirm button click.
     * This should trigger the deletion logic for the task and refresh the task list.
     */
    @FXML
    public void handleConfirm() {
        if (taskToDelete != null) {
            // Perform deletion using TaskDAO
            TaskDAO.getInstance().deleteTask(taskToDelete.getId());
            System.out.println("Task '" + taskToDelete.getTitle() + "' deleted successfully.");

            // Refresh the home page
        } else {
            System.out.println("No task provided for deletion.");
        }

        // Close the pop-up window after confirming
        closeWindow();
    }

    /**
     * Handle the Cancel button click.
     * This simply closes the pop-up window without taking any action.
     */
    @FXML
    public void handleCancel() {
        closeWindow();
    }

    /**
     * Utility method to close the current pop-up window.
     */
    private void closeWindow() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
}