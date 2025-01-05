package com.example.todolist.model;

/**
 * @brief Represents a category in the to-do list application.
 * A category is used to group tasks and can be associated with a specific user.
 *
 * @author Meftah Mohammed
 */
public class Category {
    private String name;
    private String userName;
    private boolean fullAccess;

    /**
     * @brief Default constructor for the Category class.
     *
     * Initializes a new Category with default values.
     */
    public Category() {
    }

    /**
     * @brief Constructs a new Category with the specified name and username.
     *
     * @param name The name of the category.
     * @param userName The username of the user associated with the category.
     */
    public Category(String name, String userName) {
        this.name = name;
        this.userName = userName;
        this.fullAccess = false; // Default to no full access
    }

    /**
     * @brief Constructs a new Category with the specified name, username, and full access flag.
     *
     * @param name The name of the category.
     * @param userName The username of the user associated with the category.
     * @param fullAccess A flag indicating whether the category has full access permissions.
     */
    public Category(String name, String userName, boolean fullAccess) {
        this.name = name;
        this.userName = userName;
        this.fullAccess = fullAccess;
    }

    /**
     * @brief Gets the name of the category.
     *
     * @return String The name of the category.
     */
    public String getName() {
        return name;
    }

    /**
     * @brief Sets the name of the category.
     *
     * @param name The new name of the category.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @brief Gets the username of the user associated with the category.
     *
     * @return String The username of the user associated with the category.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @brief Sets the username of the user associated with the category.
     *
     * @param userName The new username of the user associated with the category.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @brief Checks if the category has full access permissions.
     *
     * @return boolean True if the category has full access, false otherwise.
     */
    public boolean isFullAccess() {
        return fullAccess;
    }

    /**
     * @brief Sets the full access permissions for the category.
     *
     * @param fullAccess The new full access permission flag for the category.
     */
    public void setFullAccess(boolean fullAccess) {
        this.fullAccess = fullAccess;
    }

    /**
     * @brief Overrides the toString method to return the category name.
     * This method is useful for displaying the category name in UI components.
     *
     * @return String The name of the category.
     */
    @Override
    public String toString() {
        return name; // Display the category name in UI components
    }
}