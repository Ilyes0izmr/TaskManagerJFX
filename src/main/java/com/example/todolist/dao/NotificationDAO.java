package com.example.todolist.dao;

import com.example.todolist.model.NotifType;
import com.example.todolist.model.Notification;
import com.example.todolist.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    // Method to add a new notification
    public void addNotification(Notification notification, String userName) throws SQLException {
        String query = "INSERT INTO notifications (title, content, notificationType, userName) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, notification.getTitle());
            statement.setString(2, notification.getContent());
            statement.setString(3, notification.getNotificationType().toString()); // Map enum to string
            statement.setString(4, userName);
            statement.executeUpdate();
        }
    }

    // Method to retrieve all notifications for a user
    public List<Notification> getNotificationsByUser(String userName) throws SQLException {
        List<Notification> notifications = new ArrayList<>();
        String query = "SELECT * FROM notifications WHERE userName = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userName);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String title = resultSet.getString("title");
                    String content = resultSet.getString("content");
                    NotifType type = NotifType.valueOf(resultSet.getString("notificationType")); // Map string to enum
                    boolean isRead = resultSet.getBoolean("isRead");
                    Notification notification = new Notification(title, content, type, LocalDate.now());
                    notification.setRead(isRead);
                    notifications.add(notification);
                }
            }
        }
        return notifications;
    }
    public boolean notificationExists(String title, LocalDate creationDate) throws SQLException {
        String query = "SELECT COUNT(*) FROM notifications WHERE title = ? AND creationDate = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, title);
            statement.setDate(2, Date.valueOf(creationDate));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; // If count > 0, the notification already exists
                }
            }
        }
        return false;
    }

    // Method to mark a notification as read
    public void markNotificationAsRead() throws SQLException {

    }
}