package com.example.todolist.model;

import java.time.LocalDate;

public interface Task {
    public void editTask(String newTitle, String newDescription, LocalDate newDueDate, Status newStatus) ;
    void changeStatus(Status status);
}
