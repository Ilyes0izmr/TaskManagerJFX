package com.example.todolist.controller;

import com.example.todolist.model.TaskImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * @brief Controller class for the Task Details view.
 * This class is responsible for displaying the details of a selected task in the UI.
 * It binds task attributes (e.g., title, description, status) to corresponding labels
 * in the view.
 *
 * @author Izemmouren Ilyes
 */
public class TaskDetailsController {

    @FXML
    private Label titleLabel; // Label for displaying the task title

    @FXML
    private Label descriptionLabel; // Label for displaying the task description

    @FXML
    private Label statusLabel; // Label for displaying the task status

    @FXML
    private Label dueDateLabel; // Label for displaying the task due date

    @FXML
    private Label creationDateLabel; // Label for displaying the task creation date

    @FXML
    private Label priorityLabel; // Label for displaying the task priority

    @FXML
    private Label categoryLabel; // Label for displaying the task category

    @FXML
    private Label ownerLabel; // Label for displaying the task owner

    /**
     * @brief Sets the task details to be displayed in the view
     * This method updates the labels in the UI with the details of the provided task.
     *
     * @param task The task whose details are to be displayed.
     */
    public void setTask(TaskImpl task) {
        titleLabel.setText(task.getTitle());
        descriptionLabel.setText(task.getDescription());
        statusLabel.setText(task.getStatus().toString());
        dueDateLabel.setText(task.getDueDate().toString());
        creationDateLabel.setText(task.getCreationDate().toString());
        priorityLabel.setText(task.getPriority().toString());
        categoryLabel.setText(task.getCategoryName());
        ownerLabel.setText(task.getUserName());
    }
}