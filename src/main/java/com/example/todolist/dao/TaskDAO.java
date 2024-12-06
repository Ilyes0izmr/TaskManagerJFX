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
            String sqlQuery = "INSERT INTO tasks (title, description, status, dueDate, creationDate, priority, reminder, categoryId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setString(3, task.getStatus().toString());
            statement.setDate(4, Date.valueOf(task.getDueDate()));
            statement.setDate(5, Date.valueOf(task.getCreationDate())); // Setting creationDate
            statement.setString(6, task.getPriority().toString());
            statement.setString(7, task.getReminder().toString());
            statement.setInt(8, task.getCategoryId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all tasks for a specific category from the database.
     *
     * @param categoryId The ID of the category for which tasks are to be retrieved.
     * @return A list of tasks that belong to the specified category.
     */
    public ArrayList<TaskImpl> getTasksByCategory(int categoryId) {
        ArrayList<TaskImpl> tasks = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sqlQuery = "SELECT * FROM tasks WHERE categoryId = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, categoryId);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                TaskImpl task = new TaskImpl(
                        result.getInt("id"),
                        result.getString("title"),
                        result.getString("description"),
                        Status.valueOf(result.getString("status")),
                        result.getDate("dueDate").toLocalDate(),
                        result.getDate("creationDate").toLocalDate(), // Corrected for DATE type
                        Priority.valueOf(result.getString("priority")),
                        new ArrayList<>(),  // Initialize comments as an empty list
                        Reminder.valueOf(result.getString("reminder")),
                        result.getInt("categoryId")
                );
                tasks.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
}
