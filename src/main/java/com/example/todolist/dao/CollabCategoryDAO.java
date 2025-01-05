package com.example.todolist.dao;

import com.example.todolist.model.Category;
import com.example.todolist.model.User;
import com.example.todolist.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

/**
 * @brief Provides Data Access Object (DAO) methods for managing collaborative categories.
 * This class allows adding, retrieving, and deleting collaborative categories, where users
 * can share access to categories with other users.
 *
 * @author Meftah Mohammed
 */
public class CollabCategoryDAO {

    /**
     * @brief Adds a new collaboration for a category.
     *
     * @param categoryName The name of the category to be shared.
     * @param userName The username of the owner of the category.
     * @param collabUserName The username of the collaborator.
     * @param fullAccess A flag indicating whether the collaborator has full access to the category.
     * @return {@code true} if the collaboration was successfully added, {@code false} otherwise.
     *
     * @note Ensure the category name and usernames exist in the database before adding a collaboration.
     */
    public boolean addCollaboration(String categoryName, String userName, String collabUserName, boolean fullAccess) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sqlQuery = "INSERT INTO collabcategories (name, ownerUserName, collabUserName, fullAccess) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, categoryName);
            statement.setString(2, userName);
            statement.setString(3, collabUserName);
            statement.setBoolean(4, fullAccess);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @brief Retrieves all collaborative categories for a specific user.
     *
     * @param userName The username of the collaborator.
     * @return A list of collaborative categories associated with the specified user.
     *
     * @note The returned categories include the name, owner username, and full access flag.
     */
    public ArrayList<Category> getAllCollabCategories(String userName) {
        ArrayList<Category> categories = new ArrayList<>();

        String sqlQuery = "SELECT name, ownerUserName, fullAccess FROM collabcategories WHERE collabUserName = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            statement.setString(1, userName);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                String name = result.getString("name");
                String user = result.getString("ownerUserName");
                boolean fullAccess = result.getBoolean("fullAccess");

                // Create a new Category object and add it to the list
                categories.add(new Category(name, user, fullAccess));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception (consider logging frameworks in production)
        }

        return categories;
    }

    /**
     * @brief Deletes a collaborative category for a specific collaborator.
     *
     * @param category The category to be removed from the collaborator's access.
     * @return {@code true} if the collaboration was successfully deleted, {@code false} otherwise.
     *
     * @note Ensure the category and collaborator exist in the database before attempting to delete.
     */
    public boolean deleteCollabCategory(Category category) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sqlQuery = "DELETE FROM collabcategories WHERE name = ? AND ownerUserName = ? AND collabUserName = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, category.getName());
            statement.setString(2, category.getUserName());
            statement.setString(3, User.getUserName());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @brief Deletes all collaborations for a specific category.
     *
     * @param category The category for which all collaborations are to be removed.
     * @return {@code true} if all collaborations were successfully deleted, {@code false} otherwise.
     *
     * @note This method removes all collaborator access for the specified category.
     */
    public boolean deleteAllCollab(Category category) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sqlQuery = "DELETE FROM collabcategories WHERE name = ? AND ownerUserName = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, category.getName());
            statement.setString(2, category.getUserName());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}