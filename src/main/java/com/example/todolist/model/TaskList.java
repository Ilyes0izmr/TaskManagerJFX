package com.example.todolist.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

/**
 * Abstract class that represents a list of tasks in the to-do list application.
 * Provides methods to add, delete, edit, display tasks, and mark tasks as completed.
 * This class serves as a blueprint for concrete task list implementations.
 * Each TaskList is associated with a user via their username.
 * Task list AKA abstract category
 *
 * @note seems all good tbh ig we will use this instead of the task manager
 *
 * @author Meftah Mohamed+
 * @author Izemmouren ilyes
 */
public abstract class TaskList {
    protected String categoryName;      //the name of the category
    protected String userName;  // User associated with this task list
    protected ObservableList<TaskImpl> tasks;

    /**
     * Constructs a new TaskList object and initializes the list of tasks.
     *
     * @param categoryName     The name of the task list (category name).
     * @param userName The username of the user associated with the task list.
     */
    public TaskList(String categoryName, String userName) {
        this.categoryName = categoryName;
        this.userName = userName;
        tasks = FXCollections.observableArrayList();
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to add to the list.
     */
    public abstract void addTask(TaskImpl task);

    /**
     * Deletes a task from the task list.
     *
     * @param task The task to remove from the list.
     */
    public abstract void deleteTask(TaskImpl task);

    /**
     * Edits the details of an existing task in the task list.
     *
     * @param task            The task to edit.
     * @param newName         The new name for the task.
     * @param newDescription  The new description for the task.
     * @param newDueDate      The new due date for the task.
     * @param newStatus       The new status for the task.
     */
    public abstract void editTask(TaskImpl task, String newName, String newDescription, LocalDate newDueDate, Status newStatus);

    /**
     * Displays all tasks in the task list.
     */
    public abstract void displayTasks();

    /**
     * Marks a task as completed in the task list.
     *
     * @param task The task to mark as completed.
     */
    public abstract void markTaskAsCompleted(TaskImpl task);


    /**
     * Gets the name of the task list.
     *
     * @return The name of the task list.
     */
    public String getName() {
        return categoryName;
    }

    /**
     * Sets the name of the task list.
     *
     * @param categoryName The name to set for the task list.
     */
    public void setName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Gets the username of the user associated with the task list.
     *
     * @return The username of the user.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username of the user associated with the task list.
     *
     * @param userName The username to set for the task list.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the list of tasks.
     *
     * @return The list of tasks.
     */
    public ObservableList<TaskImpl> getTasks() {
        return tasks;
    }
}