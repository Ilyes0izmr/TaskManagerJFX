package com.example.todolist.dao;

import com.example.todolist.model.*;
import com.example.todolist.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public ArrayList<TaskImpl> getTasksByCategory(String categoryName, String userName) {
        ArrayList<TaskImpl> tasks = new ArrayList<>();
        String sqlQuery = "SELECT * FROM tasks WHERE userName = ? AND categoryName = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            statement.setString(1, userName);
            statement.setString(2, categoryName);
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
    public ArrayList<TaskImpl> searchTasksByTitle(String keyword, String userName, String categoryName) {
        ArrayList<TaskImpl> matchedTasks = new ArrayList<>();
        String sqlQuery;

        if (categoryName == null) {
            sqlQuery = "SELECT * FROM tasks " +
                    "WHERE title LIKE ? " +
                    "AND userName = ?";
        } else {
            sqlQuery = "SELECT * FROM tasks " +
                    "WHERE title LIKE ? " +
                    "AND userName = ? " +
                    "AND categoryName = ?";
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            // Bind parameters dynamically based on the chosen query
            statement.setString(1, "%" + keyword + "%"); // Use wildcards for partial matching
            statement.setString(2, userName); // Filter by userName

            if (categoryName != null) {
                statement.setString(3, categoryName); // Filter by categoryName if provided
            }

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
                        result.getString("categoryName"), // Fetch categoryName
                        result.getString("userName") // Fetch userName
                );
                matchedTasks.add(task);
            }
        } catch (SQLException e) {
            System.err.println("Error while searching tasks by title and userName: " + e.getMessage());
        }

        return matchedTasks;
    }

    public int getNumberOfTasks(String userName) {
        String sqlQuery = "SELECT COUNT(*) FROM tasks WHERE userName = ?";
        int numberOfTasks = 0;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            // Set the user parameter
            statement.setString(1, userName);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                numberOfTasks = result.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error while getting the number of tasks: " + e.getMessage());
        }
        return numberOfTasks;
    }

    public int getNumberOfCompleted(String userName) {
        String sqlQuery = "SELECT COUNT(*) FROM tasks WHERE status = 'COMPLETED' AND userName = ?";
        int numberOfCompletedTasks = 0;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            // Set the userName parameter
            statement.setString(1, userName);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                numberOfCompletedTasks = result.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error while getting the number of completed tasks: " + e.getMessage());
        }
        return numberOfCompletedTasks;
    }

    public Map<String, Integer> getTaskCountByCategory(String userName) {
        Map<String, Integer> taskCountByCategory = new HashMap<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            // SQL query to count tasks by category for the specified user
            String sqlQuery = "SELECT categoryName, COUNT(*) AS task_count " +
                    "FROM tasks " +
                    "WHERE userName = ? " +
                    "GROUP BY categoryName";

            System.out.println("Executing SQL Query: " + sqlQuery); // Debugging: Print the query

            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, userName); // Set the userName

            ResultSet result = statement.executeQuery();

            // Process the results
            while (result.next()) {
                String category = result.getString("categoryName"); // Use the correct column name
                int taskCount = result.getInt("task_count");

                // Handle null category names
                if (category == null) {
                    category = "Other"; // Treat null categories as "Other"
                }

                System.out.println("Category: " + category + ", Task Count: " + taskCount); // Debugging: Print the result

                // Add the category and task count to the map
                taskCountByCategory.put(category, taskCountByCategory.getOrDefault(category, 0) + taskCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return taskCountByCategory;
    }

    public Map<String, Integer> getTaskCountByPriority(String userName) {
        Map<String, Integer> taskCountByPriority = new HashMap<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            // SQL query to count tasks by priority for the specified user
            String sqlQuery = "SELECT priority, COUNT(*) AS task_count " +
                    "FROM tasks " +
                    "WHERE userName = ? " +
                    "GROUP BY priority";

            System.out.println("Executing SQL Query: " + sqlQuery); // Debugging: Print the query

            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, userName); // Set the userName

            ResultSet result = statement.executeQuery();

            // Process the results
            while (result.next()) {
                String priority = result.getString("priority"); // Use the correct column name
                int taskCount = result.getInt("task_count");

                // Handle null priority values
                if (priority == null) {
                    priority = "None"; // Treat null priorities as "None"
                }

                System.out.println("Priority: " + priority + ", Task Count: " + taskCount); // Debugging: Print the result

                // Add the priority and task count to the map
                taskCountByPriority.put(priority, taskCountByPriority.getOrDefault(priority, 0) + taskCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return taskCountByPriority;
    }

    public List<String> getUpcomingDeadlines(String userName) {
        List<String> upcomingDeadlines = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            // SQL query to fetch task titles with due dates within the next 10 days
            String sqlQuery = "SELECT title FROM tasks " +
                    "WHERE userName = ? " +
                    "AND dueDate <= DATE_ADD(CURDATE(), INTERVAL 10 DAY) " +
                    "AND dueDate >= CURDATE() " +
                    "ORDER BY dueDate ASC";

            System.out.println("Executing SQL Query: " + sqlQuery); // Debugging: Print the query

            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, userName); // Set the userName

            ResultSet result = statement.executeQuery();

            // Process the results
            while (result.next()) {
                String title = result.getString("title"); // Fetch only the title

                // Add the title to the list
                upcomingDeadlines.add(title);

                // Debugging: Print the title
                System.out.println("Task Title: " + title);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return upcomingDeadlines;
    }


}
