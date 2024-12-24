package com.example.todolist.dao;
import com.example.todolist.model.Category;
import com.example.todolist.model.User;
import com.example.todolist.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class CollabCategoryDAO {
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
