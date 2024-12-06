package com.example.todolist.model;

/**
 * Represents the possible statuses for tasks in the to-do list application.
 * The possible statuses are:
 * <ul>
 *   <li>COMPLETED: The task has been completed.</li>
 *   <li>IN_PROGRESS: The task is currently being worked on.</li>
 *   <li>PENDING: The task has not yet been started.</li>
 *   <li>ABANDONED: The task was started but later abandoned.</li>
 * </ul>
 * These values represent the lifecycle and progress of a task.
 *
 * @author Meftah Mohamed
 */
public enum Status {
    COMPLETED,
    IN_PROGRESS,
    PENDING,
    ABANDONED
}
