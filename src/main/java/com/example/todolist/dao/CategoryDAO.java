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
     * @return {@code true} if the category was successfully added, {@code false} otherwise.
     */
    public boolean addCategory(String categoryName) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sqlQuery = "INSERT INTO categories (name) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, categoryName);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves a category's name by its ID from the database.
     *
     * @param categoryId The ID of the category to be retrieved.
     * @return The name of the category, or {@code null} if the category does not exist.
     */
    public String getCategoryById(int categoryId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sqlQuery = "SELECT name FROM categories WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, categoryId);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getString("name");
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves all category names from the database.
     *
     * @return A list of category names.
     */
    public ArrayList<String> getAllCategories() {
        ArrayList<String> categories = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sqlQuery = "SELECT name FROM categories";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
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
