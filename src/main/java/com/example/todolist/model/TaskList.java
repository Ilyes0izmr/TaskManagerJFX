package com.example.todolist.model;
import java.util.ArrayList;
import java.time.LocalDate;

public abstract class TaskList {
    protected int id;
    protected String name;
    protected ArrayList<TaskImpl> tasks ;
    public TaskList() {
        tasks = new ArrayList<TaskImpl>();
    }

    public abstract void addTask(TaskImpl task);
    public abstract void deleteTask(TaskImpl task);
    public abstract void editTask(TaskImpl task, String newName, String newDescription, LocalDate newDueDate, Status newStatus);
    public abstract void displayTasks();
    public abstract void markTaskAsCompleted(TaskImpl task);

}
