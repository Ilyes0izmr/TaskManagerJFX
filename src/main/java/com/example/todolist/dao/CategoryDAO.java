package com.example.todolist.dao;
import com.example.todolist.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

/**
 * Provides Data Access Object (DAO) methods for interacting with categories in the database.
 * This class allows adding, retrieving, renaming, and checking for categories.
 *
 * @author Meftah Mohamed
 */
public class CategoryDAO {

    /**
     * Adds a new category to the database.
     *
     * @param categoryName The name of the category to be added.
     * @param userName the name of the user that created this category
     * @return {@code true} if the category was successfully added, {@code false} otherwise.
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
     * Checks if a category already exists for the user.
     *
     * @param categoryName The name of the category to check.
     * @param userName The name of the user to check the category for.
     * @return {@code true} if the category exists for the user, {@code false} otherwise.
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
     * Retrieves all categories for a specific user.
     *
     * @param userName The name of the user to fetch categories for.
     * @return A list of category names associated with the specified user.
     */
    public ArrayList<String> getAllCategories(String userName) {
        ArrayList<String> categories = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sqlQuery = "SELECT name FROM categories WHERE userName = ?"; // Fixed column name
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, userName);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                categories.add(result.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    /**
     * Renames an existing category in the database.
     *
     * @param categoryId The ID of the category to be renamed.
     * @param newName The new name for the category.
     * @return {@code true} if the category was successfully renamed, {@code false} otherwise.
     */
    public boolean rename(int categoryId, String newName) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sqlQuery = "UPDATE categories SET name = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, newName);
            statement.setInt(2, categoryId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if a category exists in the database by name.
     *
     * @param categoryName The name of the category to check.
     * @return {@code true} if the category exists, {@code false} otherwise.
     */
    public boolean getCategoryByName(String categoryName) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sqlQuery = "SELECT 1 FROM categories WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, categoryName);
            ResultSet result = statement.executeQuery();

            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
