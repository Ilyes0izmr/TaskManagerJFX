package com.example.todolist.model;
import java.time.LocalDate;
import java.util.ArrayList;

public class TaskListImpl extends TaskList {
    @Override
    public void addTask(TaskImpl task) {
        tasks.add(task);
    }

    public void deleteTask(TaskImpl task) {
        tasks.remove(task);
    }

    public void editTask(TaskImpl task, String newTitle, String newDescription, LocalDate newDueDate, Status newStatus) {
        task.editTask(newTitle,newDescription,newDueDate,newStatus);
    }

    @Override
    public void displayTasks() {
        for (TaskImpl task : tasks) {
            System.out.println("- " + task.getTitle() + " | Due: " + task.getDueDate() + " | Status: " + task.getStatus());
        }
    }

    @Override
    public void markTaskAsCompleted(TaskImpl task) {
        task.changeStatus(Status.COMPLETED);
    }



}