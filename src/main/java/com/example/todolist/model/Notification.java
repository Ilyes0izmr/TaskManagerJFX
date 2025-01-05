package com.example.todolist.model;

import java.time.LocalDate;

/**
 * @brief Represents a notification in the to-do list application.
 * This class models a notification with a title, content, type, creation date, and read status.
 *
 * @see java.time.LocalDate For the date representation used in this class.
 * @see NotifType For the type of notification.
 *
 * @author Izemmouren Ilyes
 */
public class Notification {
    private String title;
    private String content;
    private NotifType notificationType;
    private LocalDate creationDate;
    private boolean read;

    /**
     * @brief Constructs a new Notification with the specified details.
     *
     * @param title The title of the notification.
     * @param content The content or message of the notification.
     * @param notificationType The type of notification (e.g., reminder, alert).
     * @param creationDate The date when the notification was created.
     */
    public Notification(String title, String content, NotifType notificationType, LocalDate creationDate) {
        this.title = title;
        this.content = content;
        this.notificationType = notificationType;
        this.creationDate = creationDate;
    }

    /**
     * @brief Gets the title of the notification.
     *
     * @return String The title of the notification.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @brief Sets the title of the notification.
     *
     * @param title The new title of the notification.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @brief Gets the content of the notification.
     *
     * @return String The content or message of the notification.
     */
    public String getContent() {
        return content;
    }

    /**
     * @brief Sets the content of the notification.
     *
     * @param content The new content or message of the notification.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @brief Gets the type of the notification.
     *
     * @return NotifType The type of the notification.
     */
    public NotifType getNotificationType() {
        return notificationType;
    }

    /**
     * @brief Sets the type of the notification.
     *
     * @param notificationType The new type of the notification.
     */
    public void setNotificationType(NotifType notificationType) {
        this.notificationType = notificationType;
    }

    /**
     * @brief Gets the creation date of the notification.
     *
     * @return LocalDate The date when the notification was created.
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * @brief Sets the creation date of the notification.
     *
     * @param creationDate The new creation date of the notification.
     */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @brief Checks if the notification has been read.
     *
     * @return Boolean True if the notification has been read, false otherwise.
     *
     * @note This method is used to determine the read status of the notification.
     *       The application logic is responsible for setting the read status.
     */
    public Boolean isRead() {
        return read;
    }

    /**
     * @brief Sets the read status of the notification.
     *
     * @param isRead The new read status of the notification.
     */
    public void setRead(boolean isRead) {
        this.read = isRead;
    }
}