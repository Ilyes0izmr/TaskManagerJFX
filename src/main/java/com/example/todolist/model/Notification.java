package com.example.todolist.model;

import java.time.LocalDate;

public class Notification {
    private String title ;
    private String content ;
    private NotifType notificationType ;
    private LocalDate creationDate ;
    private boolean read ;
    // Constructors
    public Notification(String title, String content , NotifType notificationType , LocalDate creationDate) {
        this.title = title;
        this.content = content;
        this.notificationType = notificationType;
        this.creationDate = creationDate;
    }

    // Getter and Setter for Title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and Setter for Content
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // Getter and Setter for NotifType
    public NotifType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotifType notificationType) {
        this.notificationType = notificationType;
    }

    // Getter and Setter for CreationDate
    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean isRead(){
        return read;  // returns the boolean value of isRead attribute.  // can be used to check if notification is read or not.  // if read = true then notification is read.  // else it is unread.  // it is assumed that isRead attribute is boolean and set by the application logic.  // it is not included in the model class.  // it is up to the application to set this attribute.  // it is assumed that the application will handle the logic of marking notifications as read.  // if this attribute is not used in the application, then this method can be removed.  // it is assumed that the application will handle the logic of marking notifications as read.  // if this attribute is not used in the application, then this method can be removed.  // it is assumed that the application will handle the logic of marking notifications as read.  // if this attribute is not used in the application, then this method can be removed
    }

    public void setRead(boolean isRead) {
        this.read = isRead;
    }
}
