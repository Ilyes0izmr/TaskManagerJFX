package com.example.todolist.controller;

import com.example.todolist.model.TaskImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TaskDetailsController {

    @FXML
    private Label idLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label dueDateLabel;

    @FXML
    private Label creationDateLabel;

    @FXML
    private Label priorityLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    private Label ownerLabel;

    public void setTask(TaskImpl task) {
        idLabel.setText(String.valueOf(task.getId()));
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