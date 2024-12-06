package com.example.todolist.dao;

import com.example.todolist.model.Priority;
import com.example.todolist.model.Reminder;
import com.example.todolist.model.Status;
import com.example.todolist.model.TaskImpl;
import com.example.todolist.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

/**
 * Provides Data Access Object (DAO) methods for interacting with tasks in the database.
 * This class allows adding, retrieving, editing, and deleting tasks.
 *
 * @author Meftah Mohamed
 */
public class TaskDAO {

    /**
     * Adds a new task to the database.
     *
     * @param task The task to be added to the database.
     * @return {@code true} if the task was successfully added, {@code false} otherwise.
     */
    public boolean addTask(TaskImpl task) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sqlQuery = "INSERT INTO tasks (title, description, status, dueDate, creationDate, priority, userName) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setString(3, task.getStatus().name());
            statement.setDate(4, Date.valueOf(task.getDueDate()));
            statement.setDate(5, Date.valueOf(task.getCreationDate()));
            statement.setString(6, task.getPriority().name());
            statement.setString(7, task.getUserName()); // Include userName
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    /**
     * Retrieves a list of tasks associated with a specific user by their username.
     *
     * @param userName The username of the user whose tasks are to be retrieved.
     * @return A list of tasks belonging to the specified user.
     */
    public ArrayList<TaskImpl> getTasksByUserName(String userName) {
        ArrayList<TaskImpl> tasks = new ArrayList<>();
        String sqlQuery = "SELECT * FROM tasks WHERE userName = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            statement.setString(1, userName);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                TaskImpl task = new TaskImpl(
                        result.getInt("id"),
                        result.getString("title"),
                        result.getString("description"),
                        Status.valueOf(result.getString("status")),
                        result.getDate("dueDate").toLocalDate(),
                        result.getDate("creationDate").toLocalDate(),
                        Priority.valueOf(result.getString("priority")),
                        new ArrayList<>(), // Initialize comments as an empty list
                        Reminder.valueOf(result.getString("reminder")), // Fetch reminder
                        result.getInt("categoryId"), // Fetch categoryId
                        result.getString("userName") // Fetch userName
                );
                tasks.add(task);
            }
        } catch (SQLException e) {
            System.err.println("Error while retrieving tasks by username: " + e.getMessage());
        }
        return tasks;
    }


    /**
     * Deletes a task from the database by its ID.
     *
     * @param taskId The ID of the task to be deleted.
     * @return {@code true} if the task was successfully deleted, {@code false} otherwise.
     */
    public boolean deleteTask(int taskId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sqlQuery = "DELETE FROM tasks WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, taskId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Edits an existing task's details in the database.
     *
     * @param task The task with updated details to be saved in the database.
     * @return {@code true} if the task was successfully edited, {@code false} otherwise.
     */
    public boolean editTask(TaskImpl task) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sqlQuery = "UPDATE tasks SET title = ?, description = ?, status = ?, dueDate = ?, priority = ?, reminder = ?, categoryId = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setString(3, task.getStatus().toString());
            statement.setDate(4, Date.valueOf(task.getDueDate()));
            statement.setString(5, task.getPriority().toString());
            statement.setString(6, task.getReminder().toString());
            statement.setInt(7, task.getCategoryId());
            statement.setInt(8, task.getId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Searches for tasks by a keyword in their titles, filtered by the username of the owner.
     *
     * @param keyword  The keyword to search for in the task titles.
     * @param userName The username of the user to filter tasks by.
     * @return A list of tasks that contain the keyword in their titles and belong to the specified user.
     */
    public ArrayList<TaskImpl> searchTasksByTitle(String keyword, String userName) {
        ArrayList<TaskImpl> matchedTasks = new ArrayList<>();
        String sqlQuery = "SELECT * FROM tasks WHERE title LIKE ? AND userName = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            statement.setString(1, "%" + keyword + "%"); // Use wildcards for partial matching
            statement.setString(2, userName); // Filter by userName
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                TaskImpl task = new TaskImpl(
                        result.getInt("id"),
                        result.getString("title"),
                        result.getString("description"),
                        Status.valueOf(result.getString("status")),
                        result.getDate("dueDate").toLocalDate(),
                        result.getDate("creationDate").toLocalDate(),
                        Priority.valueOf(result.getString("priority")),
                        new ArrayList<>(), // Initialize comments as an empty list (can be fetched separately if needed)
                        Reminder.valueOf(result.getString("reminder")),
                        result.getInt("categoryId"), // Fetch categoryId
                        result.getString("userName") // Fetch userName
                );
                matchedTasks.add(task);
            }
        } catch (SQLException e) {
            System.err.println("Error while searching tasks by title and userName: " + e.getMessage());
        }
        return matchedTasks;
    }


}
