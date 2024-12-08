package com.example.todolist.model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Implements the Task interface and represents a task in the to-do list application.
 * Contains information such as title, description, due date, priority, and category, along with functionality to edit the task and change its status.
 *
 * @author Meftah Mohamed
 */
public class TaskImpl implements Task {
    private int id;
    private String title;
    private String description;
    private Status status; // Enum: COMPLETED, IN_PROGRESS, PENDING, ABANDONED
    private LocalDate dueDate;
    private LocalDate creationDate;
    private Priority priority; // Enum: LOW, MEDIUM, HIGH
    private ArrayList<Comment> comments;
    private Reminder reminder; // Enum: DAILY, WEEKLY, MONTHLY
    private String categoryName;
    private String userName;

    /**
     * Constructs a new TaskImpl with the specified details.
     *
     * //@param id           The unique identifier for the task.
     * @param title        The title of the task.
     * @param description  The description of the task.
     * @param status       The current status of the task (e.g., IN_PROGRESS).
     * @param dueDate      The due date of the task.
     * @param creationDate The creation date of the task.
     * @param priority     The priority level of the task (e.g., HIGH).
     * @param comments     The list of comments associated with the task.
     * @param reminder     The reminder frequency for the task (e.g., DAILY).
     * @param categoryName   The category ID the task belongs to.
     */
    public TaskImpl(int id ,String title, String description, Status status, LocalDate dueDate,
                    LocalDate creationDate, Priority priority, ArrayList<Comment> comments,
                    Reminder reminder, String categoryName, String userName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.creationDate = creationDate;
        this.priority = priority;
        this.comments = comments;
        this.reminder = reminder;
        this.categoryName = categoryName; // Initialize categoryName
        this.userName=userName;
    }

    /**
     * Edits the details of the task including title, description, due date, and status.
     *
     * @param newTitle       The new title of the task.
     * @param newDescription The new description of the task.
     * @param newDueDate     The new due date of the task.
     * @param newStatus      The new status of the task.
     */
    public void editTask(String newTitle, String newDescription, LocalDate newDueDate, Status newStatus) {
        this.setTitle(newTitle);
        this.setDescription(newDescription);
        this.setDueDate(newDueDate);
        this.setStatus(newStatus);
    }

    /**
     * Changes the status of the task.
     *
     * @param status The new status to set for the task.
     */
    public void changeStatus(Status status) {
        this.status = status;
    }

    /**
     * Gets the category ID of the task.
     *
     * @return The category ID of the task.
     */
    public String getCategoryName() { return categoryName; }

    /**
     * Sets the category ID of the task.
     *
     * @param categoryName The category ID to set.
     */
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    /**
     * Gets the unique ID of the task.
     *
     * @return The unique ID of the task.
     */
    public int getId() { return id; }

    /**
     * Sets the unique ID of the task.
     *
     * @param id The ID to set for the task.
     */
    public void setId(int id) { this.id = id; }

    /**
     * Gets the title of the task.
     *
     * @return The title of the task.
     */
    public String getTitle() { return title; }

    /**
     * Sets the title of the task.
     *
     * @param title The title to set for the task.
     */
    public void setTitle(String title) { this.title = title; }

    /**
     * Gets the description of the task.
     *
     * @return The description of the task.
     */
    public String getDescription() { return description; }

    /**
     * Sets the description of the task.
     *
     * @param description The description to set for the task.
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Gets the current status of the task.
     *
     * @return The status of the task.
     */
    public Status getStatus() { return status; }

    /**
     * Sets the status of the task.
     *
     * @param status The status to set for the task.
     */
    public void setStatus(Status status) { this.status = status; }

    /**
     * Gets the due date of the task.
     *
     * @return The due date of the task.
     */
    public LocalDate getDueDate() { return dueDate; }

    /**
     * Sets the due date of the task.
     *
     * @param dueDate The due date to set for the task.
     */
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    /**
     * Gets the creation date of the task.
     *
     * @return The creation date of the task.
     */
    public LocalDate getCreationDate() { return creationDate; }

    /**
     * Sets the creation date of the task.
     *
     * @param creationDate The creation date to set for the task.
     */
    public void setCreationDate(LocalDate creationDate) { this.creationDate = creationDate; }

    /**
     * Gets the priority of the task.
     *
     * @return The priority of the task.
     */
    public Priority getPriority() { return priority; }

    /**
     * Sets the priority of the task.
     *
     * @param priority The priority to set for the task.
     */
    public void setPriority(Priority priority) { this.priority = priority; }

    /**
     * Gets the list of comments associated with the task.
     *
     * @return The list of comments associated with the task.
     */
    public ArrayList<Comment> getComments() { return comments; }

    /**
     * Sets the list of comments for the task.
     *
     * @param comments The list of comments to set for the task.
     */
    public void setComments(ArrayList<Comment> comments) { this.comments = comments; }

    /**
     * Gets the reminder for the task.
     *
     * @return The reminder for the task.
     */
    public Reminder getReminder() { return reminder; }

    /**
     * Sets the reminder for the task.
     *
     * @param reminder The reminder to set for the task.
     */
    public void setReminder(Reminder reminder) { this.reminder = reminder; }

    /**
     * Gets the userName for the task.
     *
     * @return The userName for the task.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the userName for the task.
     *
     * @param userName The userName to set for the task.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String toString(){
        return "Task: "+title+", Status: "+status+", Due Date: "+dueDate;
    }
}
