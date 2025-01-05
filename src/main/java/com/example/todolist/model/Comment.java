package com.example.todolist.model;

import java.time.LocalDateTime;

/**
 * @brief Represents a comment on a task in the to-do list application.
 * A comment consists of an ID, text content, creation date, and an associated task.
 * Comments are used to provide additional context or notes for tasks. (so the user can remember fast)
 *
 * @see java.time.LocalDateTime For the date and time representation used in this class.
 * @see Task For the associated task model.
 *
 * @author Meftah Mohamed
 */
public class Comment {
    private int id;
    private String text;
    private LocalDateTime creationDate;
    private Task task;

    /**
     * @brief Constructs a new Comment with the specified ID, text, and creation date.
     *
     * @param id The unique identifier for the comment.
     * @param text The content of the comment.
     * @param creationDate The date and time when the comment was created.
     */
    public Comment(int id, String text, LocalDateTime creationDate) {
        this.id = id;
        this.text = text;
        this.creationDate = creationDate;
    }

    /**
     * @brief Gets the ID of the comment.
     *
     * @return int The unique identifier of the comment.
     */
    public int getId() {
        return id;
    }

    /**
     * @brief Sets the ID of the comment.
     *
     * @param id The unique identifier to set for the comment.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @brief Gets the text content of the comment.
     *
     * @return String The content of the comment.
     */
    public String getText() {
        return text;
    }

    /**
     * @brief Sets the text content of the comment.
     *
     * @param text The content to set for the comment.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @brief Gets the creation date and time of the comment.
     *
     * @return LocalDateTime The creation date and time of the comment.
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * @brief Sets the creation date and time of the comment.
     *
     * @param creationDate The creation date and time to set for the comment.
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @brief Gets the task associated with the comment.
     *
     * @return Task The task associated with the comment.
     */
    public Task getTask() {
        return task;
    }

    /**
     * @brief Sets the task associated with the comment.
     *
     * @param task The task to associate with the comment.
     */
    public void setTask(Task task) {
        this.task = task;
    }
}