package com.example.todolist.controller;

import com.example.todolist.dao.CommentDAO;
import com.example.todolist.model.Comment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import com.example.todolist.model.TaskImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CommentController {

    @FXML
    private TextField commentInput;

    @FXML
    private Button addButton;

    @FXML
    private ListView<Comment> taskListView;

    private ObservableList<Comment> comments = FXCollections.observableArrayList();

    private TaskImpl task;

    private CommentDAO commentDAO = new CommentDAO(); // Instantiate the DAO

    private boolean isEditing = false;

    // Initializes the controller with a task
    public void initializeTask(TaskImpl task) {
        this.task = task;
        System.out.println("Task initialized in CommentController: " + task.getTitle());
        comments.addAll(commentDAO.getCommentsByTaskId(task.getId())); // Fetch comments from the database
        taskListView.setItems(comments);
    }

    @FXML
    public void initialize() {
        // Set button action for adding comments
        addButton.setOnAction(event -> handleAddComment());

        // Configure the ListView to use custom ListCell
        taskListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Comment> call(ListView<Comment> listView) {
                return new CommentCell();
            }
        });
    }

    private void handleAddComment() {
        String newComment = commentInput.getText().trim();
        if (!newComment.isEmpty()) {
            Comment temp = new Comment(11, newComment, LocalDateTime.now());
            comments.add(temp);
            commentInput.clear();

            // Save the comment back to the task and to the database
            if (task != null) {
                task.setComments(new ArrayList<>(comments)); // Save comments in task
                commentDAO.addComment(task.getId(), newComment); // Persist comment in DB
            }
        }
    }

    // Custom ListCell class for enhanced UI with delete functionality
    private class CommentCell extends ListCell<Comment> {
        private final TextField textField = new TextField();
        private final Button deleteButton = new Button("Delete");
        private final Button editButton = new Button("Edit");
        private final Label creationDateLabel = new Label();  // Label for the creation date
        private final HBox container = new HBox(10, textField, creationDateLabel, editButton, deleteButton);  // Added the label to the container

        public CommentCell() {
            super();
            deleteButton.setOnAction(event -> handleDeleteComment());
            editButton.setOnAction(event -> handleEditComment());

            // Set properties for TextField
            textField.setPrefWidth(350);  // Set the preferred width
            textField.setMaxWidth(350);   // Optionally set the maximum width
            textField.setMinWidth(200);   // Optionally set the minimum width
            //textField.setWrapText(true);  // Enable text wrapping so that content stays inside the field

            // Set an initial height for the TextField (can be adjusted dynamically based on content)
            textField.setPrefHeight(30);  // Initial height (can be adjusted dynamically)

            // Set the container width and allow it to accommodate the growing TextField
            container.setPrefWidth(600);  // Ensure container can grow with the TextField
            textField.setEditable(false);  // Keep the textField non-editable for display
            // Ensure the container grows with the TextField
            container.setMinHeight(30);  // Min height to allow the growing effect
        }


        @Override
        protected void updateItem(Comment comment, boolean empty) {
            super.updateItem(comment, empty);
            if (empty || comment == null) {
                setGraphic(null);
            } else {
                textField.setText(comment.getText());

                // Format the date and time (e.g., "2024-12-13 15:30")
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String formattedDate = comment.getCreationDate().format(formatter);

                // Set the formatted date to the creationDateLabel
                creationDateLabel.setText(formattedDate);

                setGraphic(container);
            }
        }

        private void handleDeleteComment() {
            Comment comment = getItem();
            if (comment != null) {
                // Remove from ObservableList
                comments.remove(comment);
                if (task != null) {
                    task.setComments(new ArrayList<>(comments)); // Update task's comments list
                }
                // Delete from database
                commentDAO.deleteComment(comment.getId());
            }
        }

        private void handleEditComment() {
            Comment comment = getItem();
            if (comment != null) {
                if (!isEditing) {
                    // Start editing mode
                    textField.setEditable(true);  // Allow text editing
                    editButton.setText("Save");   // Change button text to "Save"
                    isEditing = true;  // Set the editing flag to true
                } else {
                    // Save the edited text
                    String newText = textField.getText();
                    if (!newText.trim().isEmpty()) {
                        // Update the comment's text
                        comment.setText(newText);
                        comment.setCreationDate(LocalDateTime.now()); // Update the creation date to the current time
                        commentDAO.editComment(comment.getId(), newText); // Update in the database

                        // Update the task's comments list
                        if (task != null) {
                            task.setComments(new ArrayList<>(comments));
                        }
                    }

                    // End editing mode
                    textField.setEditable(false); // Make the text field non-editable again
                    editButton.setText("Edit");   // Change button text back to "Edit"
                    isEditing = false;  // Reset the editing flag
                }
            }
        }
    }
}
