package com.example.todolist.model;
import java.time.LocalDateTime;

public class Comment {
    private int id;
    private String text;
    private LocalDateTime creationDate;
    private Task task ;

    public Comment(int id, String text, LocalDateTime creationDate) {
        this.id = id;
        this.text = text;
        this.creationDate = creationDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public LocalDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }
}
