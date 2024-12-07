package com.example.todolist.model;

/**
 * Represents a user in the application.
 *
 * @author Ilyes Izemmouren
 */
public class User {

    private String fullName;
    private static String userName;
    private String passWord;
    private String email;

    /**
     * Constructs a new User with the specified details.
     *
     * @param userName The username of the user.
     * @param fullName The full name of the user.
     * @param passWord The password of the user.
     * @param email    The email address of the user.
     */
    public User(String userName, String fullName, String passWord, String email) {
        this.userName = userName;
        this.fullName = fullName;
        this.passWord = passWord;
        this.email = email;
    }

    /**
     * Gets the full name of the user.
     *
     * @return The full name of the user.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the full name of the user.
     *
     * @param fullName The full name to set.
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username of the user.
     */
    public static String getUserName() {
        return userName;
    }

    /**
     * Sets the username of the user.
     *
     * @param userName The username to set.
     */
    public static void setUserName(String userName) {
        User.userName = userName;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassWord() {
        return passWord;
    }

    /**
     * Sets the password of the user.
     *
     * @param passWord The password to set.
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    /**
     * Gets the email address of the user.
     *
     * @return The email address of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email The email address to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns a string representation of the User object.
     *
     * @return A string representation of the User object.
     */
    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}