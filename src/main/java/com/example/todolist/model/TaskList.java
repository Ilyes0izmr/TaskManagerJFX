package com.example.todolist.model;

import java.util.ArrayList;
import java.time.LocalDate;

/**
 * Abstract class that represents a list of tasks in the to-do list application.
 * Provides methods to add, delete, edit, display tasks, and mark tasks as completed.
 * This class serves as a blueprint for concrete task list implementations.
 *
 * @author Meftah Mohamed
 */
public abstract class TaskList {
    protected int id;
    protected String name;
    protected ArrayList<TaskImpl> tasks;

    /**
     * Constructs a new TaskList object and initializes the list of tasks.
     */
    public TaskList() {
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
}
