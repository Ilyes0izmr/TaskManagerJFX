package com.example.todolist.dao;

import com.example.todolist.model.NotifType;
import com.example.todolist.model.Notification;
import com.example.todolist.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @brief Provides Data Access Object (DAO) methods for interacting with notifications in the database.
 * This class allows adding, retrieving, and managing notifications for users. It also includes
 * methods to check if a notification exists and to mark notifications as read.
 *
 * @author Izemmouren Ilyes
 */
public class NotificationDAO {

    /**
     * @brief Adds a new notification to the database.
     *
     * @param notification The notification to be added.
     * @param userName The username of the user associated with the notification.
     * @throws SQLException If a database access error occurs.
     *
     * @note The notification type is stored as a string in the database.
     * //TODO: Consider adding validation for the notification title and content to ensure they are not empty.
     */
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

    /**
     * @brief Retrieves all notifications for a specific user.
     *
     * @param userName The username of the user whose notifications are to be retrieved.
     * @return A list of notifications belonging to the specified user.
     * @throws SQLException If a database access error occurs.
     *
     * @note The notification type is converted from a string to an enum.
     * //TODO Consider adding pagination or filtering options for large datasets.
     */
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

    /**
     * @brief Checks if a notification with the specified title and creation date already exists.
     *
     * @param title The title of the notification to check.
     * @param creationDate The creation date of the notification to check.
     * @return {@code true} if the notification exists, {@code false} otherwise.
     * @throws SQLException If a database access error occurs.
     *
     * @note This method is useful for avoiding duplicate notifications.
     */
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

    /**
     * @brief Marks a notification as read in the database.
     *
     * @throws SQLException If a database access error occurs.
     */
    public void markNotificationAsRead() throws SQLException {
        // TODO: Implement this method to mark a notification as read
    }
}