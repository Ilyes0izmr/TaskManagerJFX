package com.example.todolist.controller;

import com.example.todolist.dao.CommentDAO;
import com.example.todolist.model.Comment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
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

    private class CommentCell extends ListCell<Comment> {
        private final TextField textField = new TextField();
        private final Button deleteButton = new Button("-");
        private final Button editButton = new Button("+");
        private final Label creationDateLabel = new Label();
        private final Region spacer = new Region(); // Spacer to push buttons to the right
        private final HBox container = new HBox(5, textField, spacer, creationDateLabel, editButton, deleteButton); // Date label next to buttons

        public CommentCell() {
            super();

            // Apply style classes
            container.getStyleClass().add("comment-container");
            textField.getStyleClass().add("comment-textfield");
            creationDateLabel.getStyleClass().add("due-date-label"); // Apply the due-date-label style
            editButton.getStyleClass().add("comment-button");
            deleteButton.getStyleClass().add("comment-button");
            deleteButton.getStyleClass().add("delete-button");

            // Set component sizes
            container.setPrefWidth(400); // Reduced container width
            textField.setPrefWidth(120); // Reduced text field width to give more space
            textField.setEditable(false);

            // Make the spacer grow to push buttons to the right
            HBox.setHgrow(spacer, Priority.ALWAYS);

            // Set button actions
            deleteButton.setOnAction(event -> handleDeleteComment());
            editButton.setOnAction(event -> handleEditComment());
        }

        @Override
        protected void updateItem(Comment comment, boolean empty) {
            super.updateItem(comment, empty);
            if (empty || comment == null) {
                setGraphic(null);
                getStyleClass().remove("task-cell"); // Remove the style class if the cell is empty
            } else {
                textField.setText(comment.getText());

                // Format the date and time
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String formattedDate = comment.getCreationDate().format(formatter);
                creationDateLabel.setText(formattedDate);

                setGraphic(container);
                getStyleClass().add("task-cell"); // Add the style class for the task cell
            }
        }

        private void handleDeleteComment() {
            Comment comment = getItem();
            if (comment != null) {
                comments.remove(comment);
                if (task != null) {
                    task.setComments(new ArrayList<>(comments));
                }
                commentDAO.deleteComment(comment.getId());
            }
        }

        private void handleEditComment() {
            Comment comment = getItem();
            if (comment != null) {
                if (!isEditing) {
                    textField.setEditable(true);
                    editButton.setText("Save");
                    isEditing = true;
                } else {
                    String newText = textField.getText();
                    if (!newText.trim().isEmpty()) {
                        comment.setText(newText);
                        comment.setCreationDate(LocalDateTime.now());
                        commentDAO.editComment(comment.getId(), newText);

                        if (task != null) {
                            task.setComments(new ArrayList<>(comments));
                        }
                    }

                    textField.setEditable(false);
                    editButton.setText("Edit");
                    isEditing = false;
                }
            }
        }
    }
}
