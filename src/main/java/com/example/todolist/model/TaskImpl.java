package com.example.todolist.model;
import java.time.LocalDate;
import java.util.ArrayList;

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

    public TaskImpl(int id, String title, String description, Status status, LocalDate dueDate,
                    LocalDate creationDate, Priority priority, ArrayList<Comment> comments, Reminder reminder) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.creationDate = creationDate;
        this.priority = priority;
        this.comments = comments;
        this.reminder = reminder;
    }

    public void editTask(String newTitle, String newDescription, LocalDate newDueDate, Status newStatus) {
        this.setTitle(newTitle);
        this.setDescription(newDescription);
        this.setDueDate(newDueDate);
        this.setStatus(newStatus);
    }

    public void changeStatus(Status status) {
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public LocalDate getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDate creationDate) { this.creationDate = creationDate; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public ArrayList<Comment> getComments() { return comments; }
    public void setComments(ArrayList<Comment> comments) { this.comments = comments; }

    public Reminder getReminder() { return reminder; }
    public void setReminder(Reminder reminder) { this.reminder = reminder; }

}