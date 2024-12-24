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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class TaskCell extends ListCell<TaskImpl> {
    HBox hbox = new HBox();
    CheckBox completedCheckBox = new CheckBox();
    Label taskLabel = new Label();
    Pane pane = new Pane();
    Label dueDateLabel = new Label();
    MenuButton menuButton = new MenuButton("Options");
    boolean fullAccess = true;  // Default to true, change as needed

    // Constructor modified to take the fullAccess parameter
    public TaskCell(boolean fullAccess) {
        super();
        this.fullAccess = fullAccess;  // Set the fullAccess value

        hbox.getChildren().addAll(completedCheckBox, taskLabel, pane, dueDateLabel, menuButton);
        HBox.setHgrow(pane, Priority.ALWAYS);

        // Add menu items to the MenuButton
        MenuItem editItem = new MenuItem("Edit");
        MenuItem addCommentItem = new MenuItem("Add Comment");
        MenuItem deleteItem = new MenuItem("Delete");
        MenuItem viewDetailsItem = new MenuItem("View Details");

        // Conditionally add menu items based on fullAccess
        if (fullAccess) {
            menuButton.getItems().addAll(editItem, addCommentItem, deleteItem, viewDetailsItem);
        } else {
            menuButton.getItems().addAll(addCommentItem, viewDetailsItem);
        }

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
                taskDAO.editTask(task);
            } else if (task != null && !completedCheckBox.isSelected()) {
                TaskDAO taskDAO = new TaskDAO();
                task.changeStatus(Status.PENDING);
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
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/todolist/view/fxml/CommentsView.fxml"));
            Pane pane = loader.load();

            // Pass the task to the CommentController
            CommentController controller = loader.getController();
            controller.initializeTask(task);

            // Set up a new stage for displaying the comment UI
            Stage commentStage = new Stage();
            commentStage.setTitle("Add Comment");
            commentStage.setScene(new Scene(pane));
            commentStage.initModality(Modality.APPLICATION_MODAL); // Block interaction with the main window

            // Show the stage and wait for it to close
            commentStage.showAndWait();

            System.out.println("Comment window closed for task: " + task.getTitle());
        } catch (IOException e) {
            System.err.println("Error loading CommentsView.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void handleEditTask(TaskImpl task) {
        try {
            // Load the FXML file for the edit task view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/todolist/view/fxml/EditTaskView.fxml"));
            Pane pane = loader.load();

            // Get the controller for the EditTaskView
            EditTaskController controller = loader.getController();
            controller.setTask(task); // Pass the task to be edited to the controller

            // Create a new stage for the edit task window
            Stage stage = new Stage();
            stage.setTitle("Edit Task");
            stage.setScene(new Scene(pane));

            // Make the stage modal, so the user must close it to interact with the main window
            stage.initModality(Modality.APPLICATION_MODAL);

            // Show the window
            stage.showAndWait();  // Use showAndWait to block interaction until the window is closed

            // Optionally, refresh the main view after closing the edit window
            HomeController.getInstance().refresh();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading EditTaskView.");
        }
    }

    private void handleDeleteTask(TaskImpl task) {
        try {
            // Load the FXML file for the delete confirmation dialog
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/todolist/view/fxml/DeleteTaskView.fxml"));
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/com/example/todolist/view/fxml/HomeView.fxml"));
            Pane pane = loader.load();

            // Get the controller for the DeleteTaskView
            DeleteTaskController controller = loader.getController();
            controller.setTask(task); // Pass the task to be deleted to the controller

            // Create a new stage for the delete confirmation dialog
            Stage popupStage = new Stage();
            popupStage.setTitle("Delete Task");
            popupStage.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows
            popupStage.setResizable(false);
            popupStage.setScene(new Scene(pane));
            // Refresh the task list after deletion
            // Show the popup and wait for it to close
            popupStage.showAndWait();
            HomeController.getInstance().refresh();

            System.out.println("Delete task: " + task.getTitle());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading DeleteTaskView.");
        }
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