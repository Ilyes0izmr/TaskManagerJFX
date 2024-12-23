package com.example.todolist.model;

public class Category {
    private String name;
    private String userName;
    private boolean fullAccess;

    // Constructors
    public Category() {
    }

    public Category(String name, String userName) {
        this.name = name;
        this.userName = userName;
        this.fullAccess = false;
    }

    public Category(String name, String userName, boolean fullAccess) {
        this.name = name;
        this.userName = userName;
        this.fullAccess = fullAccess;
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

    public boolean isFullAccess() {
        return fullAccess;
    }

    public void setFullAccess(boolean fullAccess) {
        this.fullAccess = fullAccess;
    }
}
