package com.example.todolist.model;

import java.util.ArrayList;
import java.time.LocalDate;

/**
 * Abstract class that represents a list of tasks in the to-do list application.
 * Provides methods to add, delete, edit, display tasks, and mark tasks as completed.
 * This class serves as a blueprint for concrete task list implementations.
 * Each TaskList is associated with a user via their username.
 *
 * @author Meftah Mohamed
 */
public abstract class TaskList {
    protected int id;
    protected String name;
    protected String userName;  // User associated with this task list
    protected ArrayList<TaskImpl> tasks;

    /**
     * Constructs a new TaskList object and initializes the list of tasks.
     *
     * @param name     The name of the task list (category name).
     * @param userName The username of the user associated with the task list.
     */
    public TaskList(String name, String userName) {
        this.name = name;
        this.userName = userName;
        tasks = new ArrayList<TaskImpl>();
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
     * Gets the ID of the task list.
     *
     * @return The ID of the task list.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the task list.
     *
     * @param id The ID to set for the task list.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the task list.
     *
     * @return The name of the task list.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the task list.
     *
     * @param name The name to set for the task list.
     */
    public void setName(String name) {
        this.name = name;
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
}
