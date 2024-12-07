package com.example.todolist.controller;

import com.example.todolist.dao.TaskDAO;
import com.example.todolist.model.Status;
import com.example.todolist.model.TaskImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.IOException;

public class TaskCell extends ListCell<TaskImpl> {
    HBox hbox = new HBox();
    CheckBox completedCheckBox = new CheckBox();
    Label taskLabel = new Label();
    Pane pane = new Pane();
    Label dueDateLabel = new Label();
    MenuButton menuButton = new MenuButton("Options");

    public TaskCell() {
        super();
        hbox.getChildren().addAll(completedCheckBox, taskLabel, pane, dueDateLabel, menuButton);
        HBox.setHgrow(pane, Priority.ALWAYS);

        // Add menu items to the MenuButton
        MenuItem editItem = new MenuItem("Edit");
        MenuItem addCommentItem = new MenuItem("Add Comment");
        MenuItem deleteItem = new MenuItem("Delete");
        MenuItem viewDetailsItem = new MenuItem("View Details");
        menuButton.getItems().addAll(editItem, addCommentItem, deleteItem, viewDetailsItem);

        // Add event handlers for the menu items
        editItem.setOnAction(event -> {
            TaskImpl task = getItem();
            if (task != null) {
                handleEditTask(task);
            }
        });

        addCommentItem.setOnAction(event -> {
            TaskImpl task = getItem();
            if (task != null) {
                handleAddComment(task);
            }
        });

        deleteItem.setOnAction(event -> {
            TaskImpl task = getItem();
            if (task != null) {
                handleDeleteTask(task);
            }
        });

        viewDetailsItem.setOnAction(event -> {
            TaskImpl task = getItem();
            if (task != null) {
                handleViewDetails(task);
            }
        });

        // Add event handler for the CheckBox
        completedCheckBox.setOnAction(event -> {
            TaskImpl task = getItem();
            if (task != null && completedCheckBox.isSelected()) {
                TaskDAO taskDAO = new TaskDAO();
                task.changeStatus(Status.COMPLETED);
                System.out.println(task.getTitle() + ": " + task.getStatus());
                taskDAO.editTask(task);
            }
        });
    }

    @Override
    protected void updateItem(TaskImpl task, boolean empty) {
        super.updateItem(task, empty);
        if (empty || task == null) {
            setGraphic(null);
        } else {
            taskLabel.setText(task.getTitle());
            dueDateLabel.setText("Due: " + task.getDueDate());
            completedCheckBox.setSelected(task.getStatus() == Status.COMPLETED);
            setGraphic(hbox);
        }
    }

    private void handleAddComment(TaskImpl task) {
        // Implement the logic to add a comment to the task
        System.out.println("Add comment to task: " + task.getTitle());
    }

    private void handleEditTask(TaskImpl task) {
        // Implement the logic to edit the task
        System.out.println("Edit task: " + task.getTitle());
    }

    private void handleDeleteTask(TaskImpl task) {
        // Implement the logic to delete the task
        System.out.println("Delete task: " + task.getTitle());
    }

    private void handleViewDetails(TaskImpl task) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/todolist/view/fxml/TaskDetailsView.fxml"));
            Pane pane = loader.load();
            TaskDetailsController controller = loader.getController();
            controller.setTask(task);

            Stage stage = new Stage();
            stage.setTitle("Task Details");
            stage.setScene(new Scene(pane));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}