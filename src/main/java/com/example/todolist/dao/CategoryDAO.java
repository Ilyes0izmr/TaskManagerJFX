package com.example.todolist.dao;

import com.example.todolist.model.Category;
import com.example.todolist.model.User;
import com.example.todolist.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

/**
 * @brief Provides Data Access Object (DAO) methods for interacting with categories in the database.
 * This class allows adding, retrieving, renaming, deleting, and searching for categories.
 * Categories are associated with specific users and can be managed through this class.
 *
 * @author Meftah Mohamed
 */
public class CategoryDAO {

    /**
     * @brief Adds a new category to the database.
     *
     * @param categoryName The name of the category to be added.
     * @param userName The username of the user creating the category.
     * @return {@code true} if the category was successfully added, {@code false} otherwise.
     *
     * @note This method checks if the category already exists for the user before adding it.
     */
    public boolean addCategory(String categoryName, String userName) {
        // First, check if the category already exists for the user
        if (getCategoryByNameAndUser(categoryName, userName)) {
            System.out.println("Category already exists for the user.");
            return false; // Category already exists for this user
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sqlQuery = "INSERT INTO categories (name, userName) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, categoryName);
            statement.setString(2, userName); // Set the userName

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @brief Checks if a category already exists for the user.
     *
     * @param categoryName The name of the category to check.
     * @param userName The username of the user to check the category for.
     * @return {@code true} if the category exists for the user, {@code false} otherwise.
     *
     * @note This method is used to avoid duplicate categories for the same user.
     */
    public boolean getCategoryByNameAndUser(String categoryName, String userName) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sqlQuery = "SELECT 1 FROM categories WHERE name = ? AND userName = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, categoryName);
            statement.setString(2, userName);
            ResultSet result = statement.executeQuery();

            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @brief Searches for categories by a keyword, filtered by the username of the owner.
     *
     * @param keyword The keyword to search for in the category names.
     * @param userName The username of the user to filter categories by.
     * @return A list of categories that match the keyword and belong to the specified user.
     *
     * @note The search is case-insensitive and matches partial category names.
     */
    public ArrayList<Category> searchCategoriesByKeyword(String keyword, String userName) {
        ArrayList<Category> categories = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            // SQL query to search categories by keyword (case-insensitive) and userName
            String sqlQuery = "SELECT * FROM categories WHERE (name LIKE ? OR name LIKE ? OR name LIKE ?) AND userName = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);

            // Set parameters for the query
            statement.setString(1, "%" + keyword + "%"); // Case-sensitive
            statement.setString(2, "%" + keyword.toUpperCase() + "%"); // Uppercase
            statement.setString(3, "%" + keyword.toLowerCase() + "%"); // Lowercase
            statement.setString(4, userName); // Filter by userName

            // Execute the query
            ResultSet result = statement.executeQuery();

            // Process the results
            while (result.next()) {
                // Create a Category object from the result set
                Category category = new Category(
                        result.getString("name"),
                        result.getString("userName")
                );
                categories.add(category); // Add the category to the list
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories; // Return the list of categories
    }

    /**
     * @brief Retrieves all categories for a specific user.
     *
     * @param userName The username of the user to fetch categories for.
     * @return A list of categories associated with the specified user.
     *
     * @note This method returns all categories owned by the user, including their names and usernames.
     */
    public ArrayList<Category> getAllCategories(String userName) {
        ArrayList<Category> categories = new ArrayList<>();

        String sqlQuery = "SELECT name, userName FROM categories WHERE userName = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            statement.setString(1, userName);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                String name = result.getString("name");
                String user = result.getString("userName");

                // Create a new Category object and add it to the list
                categories.add(new Category(name, user));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception (consider logging frameworks in production)
        }

        return categories;
    }

    /**
     * @brief Renames an existing category in the database.
     *
     * @param categoryName The current name of the category.
     * @param userName The username of the user who owns the category.
     * @param newName The new name for the category.
     * @return {@code true} if the category was successfully renamed, {@code false} otherwise.
     *
     * @note Ensure the category exists before attempting to rename it.
     */
    public boolean rename(String categoryName, String userName, String newName) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sqlQuery = "UPDATE categories SET name = ? WHERE name = ? AND userName = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, newName);
            statement.setString(2, categoryName);
            statement.setString(3, userName);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @brief Deletes a category from the database.
     *
     * @param name The name of the category to be deleted.
     * @return {@code true} if the category was successfully deleted, {@code false} otherwise.
     *
     * @note This method deletes the category only if it belongs to the currently logged-in user.
     */
    public boolean deleteCategory(String name) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sqlQuery = "DELETE FROM categories WHERE name = ? AND userName = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, name);
            statement.setString(2, User.getUserName());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}