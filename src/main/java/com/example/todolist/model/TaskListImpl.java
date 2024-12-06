package com.example.todolist.model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Concrete implementation of the TaskList abstract class.
 * This class provides the specific implementations for managing a list of tasks.
 * It supports adding, deleting, editing, displaying tasks, and marking tasks as completed.
 *
 * @author Meftah Mohamed
 */
public class TaskListImpl extends TaskList {

    /**
     * Constructs a new TaskList object and initializes the list of tasks.
     *
     * @param name
     * @param userName
     */
    public TaskListImpl(String name, String userName) {
        super(name, userName);
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to be added to the list.
     */
    @Override
    public void addTask(TaskImpl task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the task list.
     *
     * @param task The task to be removed from the list.
     */
    public void deleteTask(TaskImpl task) {
        tasks.remove(task);
    }

    /**
     * Edits an existing task's details in the task list.
     *
     * @param task            The task to be edited.
     * @param newTitle        The new title for the task.
     * @param newDescription  The new description for the task.
     * @param newDueDate      The new due date for the task.
     * @param newStatus       The new status for the task.
     */
    public void editTask(TaskImpl task, String newTitle, String newDescription, LocalDate newDueDate, Status newStatus) {
        task.editTask(newTitle, newDescription, newDueDate, newStatus);
    }

    /**
     * Displays all tasks in the task list with their title, due date, and status.
     */
    @Override
    public void displayTasks() {
        for (TaskImpl task : tasks) {
            System.out.println("- " + task.getTitle() + " | Due: " + task.getDueDate() + " | Status: " + task.getStatus());
        }
    }

    /**
     * Marks a task as completed in the task list.
     *
     * @param task The task to be marked as completed.
     */
    @Override
    public void markTaskAsCompleted(TaskImpl task) {
        task.changeStatus(Status.COMPLETED);
    }
}
