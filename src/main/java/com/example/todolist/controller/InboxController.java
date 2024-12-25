package com.example.todolist.controller;

import com.example.todolist.dao.NotificationDAO;
import com.example.todolist.dao.TaskDAO;
import com.example.todolist.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InboxController {

    @FXML
    private ListView<Notification> notificationListView;

    @FXML
    private Label footerLabel;

    @FXML
    private TextField searchTextField;

    private TaskDAO taskDAO;
    private NotificationDAO notificationDAO;
    private ObservableList<Notification> allNotifications;
    private FilteredList<Notification> filteredNotifications;


    public InboxController() {
        taskDAO = new TaskDAO();
        notificationDAO = new NotificationDAO();

    }

    @FXML
    public void initialize() {
        hundlReminder();
        // Load notifications from the database
        try {
            List<Notification> notifications = notificationDAO.getNotificationsByUser(User.getUserName()); // Replace with actual username
            allNotifications = FXCollections.observableArrayList(notifications);

            // Wrap the ObservableList in a FilteredList for search functionality
            filteredNotifications = new FilteredList<>(allNotifications, n -> true);

            // Set the custom ListCell for the ListView
            notificationListView.setCellFactory(listView -> new NotificationListCell());
            notificationListView.setItems(filteredNotifications);

            // Update the footer label with the number of unread notifications
            long unreadCount = notifications.stream().filter(n -> !n.isRead()).count();
            footerLabel.setText("You have " + unreadCount + " unread notifications.");

            // Handle double-click on a notification
            notificationListView.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) { // Double-click
                    Notification selectedNotification = notificationListView.getSelectionModel().getSelectedItem();
                    if (selectedNotification != null) {
                        showNotificationContent(selectedNotification);
                    }
                }
            });

            // Add a listener to the search text field
            searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filterNotifications(newValue);
            });

        } catch (SQLException e) {
            e.printStackTrace();
            footerLabel.setText("Failed to load notifications.");
        }
    }

    private void showNotificationContent(Notification notification) {
        // Show the notification content in an alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(notification.getTitle());
        alert.setHeaderText(null);
        alert.setContentText(notification.getContent());
        alert.showAndWait();
    }

    private void filterNotifications(String searchText) {
        filteredNotifications.setPredicate(notification -> {
            if (searchText == null || searchText.isEmpty()) {
                return true; // Show all notifications if search text is empty
            }

            // Filter by title or content
            String lowerCaseSearchText = searchText.toLowerCase();
            return notification.getTitle().toLowerCase().contains(lowerCaseSearchText) ||
                    notification.getContent().toLowerCase().contains(lowerCaseSearchText);
        });
    }

    private void hundlReminder() {
        try {
            // Get all tasks for the current user
            ArrayList<TaskImpl> tasks = taskDAO.getTasksByUserName(User.getUserName());
            LocalDate today = LocalDate.now();

            for (TaskImpl task : tasks) {
                // Regular Reminders
                if (isReminderDay(task.getCreationDate(), task.getReminder(), today) &&
                        !notificationDAO.notificationExists(task.getReminder() + " Reminder", today)) {
                    Notification notification = new Notification(
                            task.getReminder() + " Reminder", // e.g., "WEEKLY Reminder"
                            "You have a " + task.getReminder().toString().toLowerCase() + " reminder for: " + task.getTitle() + ".",
                            NotifType.TASK,
                            LocalDate.now()
                    );
                    notificationDAO.addNotification(notification, User.getUserName());
                }

                // Due Date Reminders (3 Days Before)
                if (today.equals(task.getDueDate().minusDays(3)) &&
                        !notificationDAO.notificationExists("Due Date Approaching", today)) {
                    Notification notification = new Notification(
                            "Due Date Approaching",
                            "The task '" + task.getTitle() + "' is due in 3 days. Make sure to finish it before then.",
                            NotifType.TASK,
                            LocalDate.now()
                    );
                    notificationDAO.addNotification(notification, User.getUserName());
                }

                // Due Date Reminders (1 Day Before)
                if (today.equals(task.getDueDate().minusDays(1)) &&
                        !notificationDAO.notificationExists("Due Date Approaching", today)) {
                    Notification notification = new Notification(
                            "Due Date Approaching",
                            "The task '" + task.getTitle() + "' is due tomorrow. Make sure to finish it before then.",
                            NotifType.TASK,
                            LocalDate.now()
                    );
                    notificationDAO.addNotification(notification, User.getUserName());
                }

                // Overdue Reminders
                if (today.isAfter(task.getDueDate()) &&
                        !notificationDAO.notificationExists("Task Overdue", today)) {
                    Notification notification = new Notification(
                            "Task Overdue",
                            "The task '" + task.getTitle() + "' is overdue. I hope there is no parchment for you :(",
                            NotifType.TASK,
                            LocalDate.now()
                    );
                    notificationDAO.addNotification(notification, User.getUserName());

                    // Update task status to OVERDUE
                    task.setStatus(Status.OVERDUE);
                    taskDAO.editTask(task); // Save the updated task to the database
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error handling reminders: " + e.getMessage());
        }
    }

    private boolean isReminderDay(LocalDate creationDate, Reminder reminder, LocalDate today) {
        if (creationDate == null || reminder == null) {
            return false;
        }

        return switch (reminder) {
            case DAILY ->
                // Send a reminder every day
                    true;
            case WEEKLY ->
                // Send a reminder every week on the same day as the creation date
                    today.getDayOfWeek() == creationDate.getDayOfWeek();
            case MONTHLY ->
                // Send a reminder every month on the same day of the month as the creation date
                    today.getDayOfMonth() == creationDate.getDayOfMonth();
            default -> false; // Unknown reminder interval
        };
    }


    // Inner private class for custom ListCell
    private class NotificationListCell extends ListCell<Notification> {

        @Override
        protected void updateItem(Notification notification, boolean empty) {
            super.updateItem(notification, empty);

            if (empty || notification == null) {
                setText(null);
                setGraphic(null);
            } else {
                // Create the UI for the notification
                HBox hbox = new HBox(10);
                hbox.setPadding(new Insets(10));

                // Title
                Label titleLabel = new Label(notification.getTitle());
                titleLabel.setFont(Font.font("Arial", 16));

                // Notification Type
                Label typeLabel = new Label(notification.getNotificationType().toString());
                typeLabel.setFont(Font.font("Arial", 14));

                // Empty Pane (for spacing)
                VBox emptyPane = new VBox();

                // Creation Date
                Label dateLabel = new Label("Created: " + notification.getCreationDate().toString());
                dateLabel.setFont(Font.font("Arial", 12));

                // Add all elements to the HBox
                hbox.getChildren().addAll(titleLabel, typeLabel, emptyPane, dateLabel);

                // Set the HBox as the graphic for this ListCell
                setGraphic(hbox);
            }
        }
    }
}