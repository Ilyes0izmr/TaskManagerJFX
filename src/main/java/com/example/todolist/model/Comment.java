package com.example.todolist.model;

import java.time.LocalDateTime;

/**
 * Represents a comment on a task in the to-do list application.
 * A comment consists of an ID, text, and a creation date.
 *
 * @author Meftah Mohamed
 */
public class Comment {
    private int id;
    private String text;
    private LocalDateTime creationDate;
    private Task task ;

    /**
     * Constructs a new Comment with the specified ID, text, and creation date.
     *
     * @param id            The unique identifier for the comment.
     * @param text          The content of the comment.
     * @param creationDate  The date and time when the comment was created.
     */
    public Comment(int id, String text, LocalDateTime creationDate) {
        this.id = id;
        this.text = text;
        this.creationDate = creationDate;
    }

    /**
     * Gets the ID of the comment.
     *
     * @return The unique identifier of the comment.
     */
    public int getId() { return id; }

    /**
     * Sets the ID of the comment.
     *
     * @param id The unique identifier to set for the comment.
     */
    public void setId(int id) { this.id = id; }

    /**
     * Gets the text content of the comment.
     *
     * @return The content of the comment.
     */
    public String getText() { return text; }

    /**
     * Sets the text content of the comment.
     *
     * @param text The content to set for the comment.
     */
    public void setText(String text) { this.text = text; }

    /**
     * Gets the creation date and time of the comment.
     *
     * @return The creation date and time of the comment.
     */
    public LocalDateTime getCreationDate() { return creationDate; }

    /**
     * Sets the creation date and time of the comment.
     *
     * @param creationDate The creation date and time to set for the comment.
     */
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }
}
