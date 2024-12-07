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
    private static TaskDAO instance;



    /**
     * Adds a new task to the database.
     *
     * @param task The task to be added to the database.
     * @return {@code true} if the task was successfully added, {@code false} otherwise.
     */
    public boolean addTask(TaskImpl task) {
        String reminderName=null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sqlQuery = "INSERT INTO tasks (title, description, status, dueDate, creationDate, priority, reminder ,userName ,categoryName ) VALUES (?, ?, ?, ?, ?, ?, ? ,? ,?)";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setString(3, task.getStatus().name());
            statement.setDate(4, Date.valueOf(task.getDueDate()));
            statement.setDate(5, Date.valueOf(task.getCreationDate()));

            if (task.getPriority() != null) {
                statement.setString(6, task.getPriority().name());
            } else {
                statement.setString(6, Priority.LOW.name()); // Use null if no priority is set
            }
            if (task.getReminder() != null) {
                statement.setString(7, task.getReminder().name());
            }
            else{
                statement.setString(7, Reminder.WEEKLY.name());
            }

            statement.setString(8, task.getUserName());
            statement.setString(9, task.getCategoryName());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static synchronized TaskDAO getInstance() {
        if (instance == null){
            instance = new TaskDAO();
        }
        return instance;
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
                String priorityStr = result.getString("priority");
                Priority priority = (priorityStr != null) ? Priority.valueOf(priorityStr) : null;

                String reminderStr = result.getString("reminder");
                Reminder reminder = (reminderStr != null) ? Reminder.valueOf(reminderStr) : null;

                TaskImpl task = new TaskImpl(
                        result.getInt("id"),
                        result.getString("title"),
                        result.getString("description"),
                        Status.valueOf(result.getString("status")),
                        result.getDate("dueDate").toLocalDate(),
                        result.getDate("creationDate").toLocalDate(),
                        priority,
                        new ArrayList<>(), // Initialize comments as an empty list
                        reminder,
                        result.getString("categoryName"), // Fetch categoryId
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
            String sqlQuery = "UPDATE tasks SET title = ?, description = ?, status = ?, dueDate = ?, priority = ?, reminder = ?, categoryName = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setString(3, task.getStatus().toString());
            statement.setDate(4, Date.valueOf(task.getDueDate()));
            statement.setString(5, task.getPriority().toString());
            statement.setString(6, task.getReminder().toString());
            statement.setString(7, task.getCategoryName());
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
                        result.getString("categoryName"), // Fetch categoryId
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
