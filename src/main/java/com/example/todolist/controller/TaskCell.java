package com.example.todolist.controller;

import com.example.todolist.dao.TaskDAO;
import com.example.todolist.model.Status;
import com.example.todolist.model.TaskImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/todolist/view/fxml/CommentsView.fxml"));
            Pane pane = loader.load();
            CommentController controller = loader.getController();
            controller.initializeTask(task);

            Stage commentStage = new Stage();
            commentStage.setTitle("Add Comment");
            commentStage.setScene(new Scene(pane));
            commentStage.initModality(Modality.APPLICATION_MODAL);
            commentStage.showAndWait();

            System.out.println("Comment window closed for task: " + task.getTitle());
        } catch (IOException e) {
            System.err.println("Error loading CommentsView.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleEditTask(TaskImpl task) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/todolist/view/fxml/EditTaskView.fxml"));
            Pane pane = loader.load();
            EditTaskController controller = loader.getController();
            controller.setTask(task);

            Stage stage = new Stage();
            stage.setTitle("Edit Task");
            stage.setScene(new Scene(pane));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            HomeController.getInstance().refresh();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading EditTaskView.");
        }
    }

    private void handleDeleteTask(TaskImpl task) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/todolist/view/fxml/DeleteTaskView.fxml"));
            Pane pane = loader.load();
            DeleteTaskController controller = loader.getController();
            controller.setTask(task);

            Stage popupStage = new Stage();
            popupStage.setTitle("Delete Task");
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setResizable(false);
            popupStage.setScene(new Scene(pane));
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
