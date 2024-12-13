package com.example.todolist.model;

public class Category {
    private String name;
    private String userName;

    // Constructors
    public Category() {
    }

    public Category(String name, String userName) {
        this.name = name;
        this.userName = userName;
    }

    // Getter and Setter for Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for UserName
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Optional: Override toString for display purposes
    @Override
    public String toString() {
        return name; // Display the category name in UI components
    }
}
