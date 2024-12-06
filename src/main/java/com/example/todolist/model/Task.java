package com.example.todolist.model;

import java.time.LocalDate;

/**
 * Represents a task in the to-do list application.
 * This interface defines the necessary operations for manipulating tasks.
 * Tasks include attributes such as title, description, status, and due date.
 *
 * @author Meftah Mohamed
 */
public interface Task {

    /**
     * Edits the details of the task including title, description, due date, and status.
     *
     * @param newTitle       The new title of the task.
     * @param newDescription The new description of the task.
     * @param newDueDate     The new due date of the task.
     * @param newStatus      The new status of the task.
     */
    public void editTask(String newTitle, String newDescription, LocalDate newDueDate, Status newStatus);

    /**
     * Changes the status of the task.
     *
     * @param status The new status to set for the task.
     * @return void
     */
    void changeStatus(Status status);
}
