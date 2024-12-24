package com.example.todolist.controller;

import com.example.todolist.dao.NotificationDAO;
import com.example.todolist.model.Notification;
import com.example.todolist.model.User;
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
import java.util.List;

public class InboxController {

    @FXML
    private ListView<Notification> notificationListView;

    @FXML
    private Label footerLabel;

    @FXML
    private TextField searchTextField;

    private NotificationDAO notificationDAO;
    private ObservableList<Notification> allNotifications;
    private FilteredList<Notification> filteredNotifications;

    public InboxController() {
        // Initialize the DAO
        notificationDAO = new NotificationDAO();
    }

    @FXML
    public void initialize() {
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

    //TODO:add the logic to add in the inbox notification about remuinders nad the collaborations


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