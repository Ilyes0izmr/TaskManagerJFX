package com.example.todolist.controller;

import com.example.todolist.dao.TaskDAO;
import com.example.todolist.model.Status;
import com.example.todolist.model.TaskImpl;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class TaskCell extends ListCell<TaskImpl> {
    HBox hbox = new HBox();
    CheckBox completedCheckBox = new CheckBox();
    Label taskLabel = new Label();
    Pane pane = new Pane();
    Label priorityLabel = new Label();
    Label dueDateLabel = new Label();
    MenuButton menuButton = new MenuButton("Options");
    boolean fullAccess = true;  // Default to true, change as needed

    public TaskCell(boolean fullAccess) {
        super();
        this.fullAccess = fullAccess;
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setSpacing(10);

        // Set alignment for individual elements
        taskLabel.setAlignment(Pos.CENTER_LEFT);
        priorityLabel.setAlignment(Pos.CENTER);
        dueDateLabel.setAlignment(Pos.CENTER);

        // Add all elements to HBox
        hbox.getChildren().addAll(completedCheckBox, taskLabel, pane, priorityLabel, dueDateLabel, menuButton);
        HBox.setHgrow(pane, Priority.ALWAYS);

        // Set consistent vertical margins
        HBox.setMargin(taskLabel, new Insets(0, 0, 0, 10));
        HBox.setMargin(priorityLabel, new Insets(0, 5, 0, 5));
        HBox.setMargin(dueDateLabel, new Insets(0, 5, 0, 5));

        // Apply style classes
        hbox.getStyleClass().add("task-hbox");
        completedCheckBox.getStyleClass().add("task-checkbox");
        taskLabel.getStyleClass().add("task-label");
        dueDateLabel.getStyleClass().add("due-date-label");
        menuButton.getStyleClass().add("menu-button");
        priorityLabel.getStyleClass().add("priority-label");


        MenuItem editItem = new MenuItem("Edit");
        MenuItem addCommentItem = new MenuItem("Add Comment");
        MenuItem deleteItem = new MenuItem("Delete");
        MenuItem viewDetailsItem = new MenuItem("View Details");

        if (fullAccess) {
            menuButton.getItems().addAll(editItem, addCommentItem, deleteItem, viewDetailsItem);
        } else {
            menuButton.getItems().addAll(addCommentItem, viewDetailsItem);
        }

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

        completedCheckBox.setOnAction(event -> {
            TaskImpl task = getItem();
            if (task != null) {
                TaskDAO taskDAO = new TaskDAO();
                if (completedCheckBox.isSelected()) {
                    task.changeStatus(Status.COMPLETED);
                } else {
                    task.changeStatus(Status.PENDING);
                }
                taskDAO.editTask(task);
                updateItem(task, false); // Refresh the cell to apply styling
            }
        });
    }

    @Override
    protected void updateItem(TaskImpl task, boolean empty) {
        super.updateItem(task, empty);

        if (empty || task == null) {
            setText(null);
            setGraphic(null);
            setStyle(""); // Reset styling for empty cells
        } else {
            taskLabel.setText(task.getTitle());

            // Apply priority styling
            priorityLabel.getStyleClass().removeAll("priority-label-HIGH", "priority-label-MEDIUM", "priority-label-LOW");
            priorityLabel.getStyleClass().add("priority-label-" + task.getPriority());
            priorityLabel.setText(String.valueOf(task.getPriority()));

            // Set due date
            if (task.getDueDate() != null) {
                dueDateLabel.setText("      "+task.getDueDate().toString()+"      ");
            } else {
                dueDateLabel.setText("");
            }

            // Set checkbox state
            completedCheckBox.setSelected(task.getStatus() == Status.COMPLETED);

            // Apply styling based on task status and due date
            if (task.getStatus() == Status.COMPLETED) {
                // style for completed tasks
                setStyle("-fx-background-color: #e0f7fa;"); // Light blue background
            } else if (task.getDueDate() != null && task.getDueDate().isBefore(LocalDate.now())) {
                // style for overdue tasks
                setStyle("-fx-background-color: #ffebee;"); // Light red background
            } else {
                // default style for pending tasks
                setStyle(""); // Reset to default
            }

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
