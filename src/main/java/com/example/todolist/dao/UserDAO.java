package com.example.todolist.dao;

import com.example.todolist.model.User;
import com.example.todolist.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    /**
     * Authenticates a user based on username and password.
     *
     * @param userName The username of the user.
     * @param passWord The password of the user.
     * @return true if the user is authenticated, false otherwise.
     */
    public boolean login(String userName, String passWord) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sqlQuery = "SELECT * FROM users WHERE email = ? AND passWord = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, userName);
            statement.setString(2, passWord);
            ResultSet result = statement.executeQuery();
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Registers a new user.
     *
     * @param user The User object containing the user details.
     * @return true if the user is registered successfully, false otherwise.
     */
    public boolean signUp(User user) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sqlQuery = "INSERT INTO users (userName, passWord, fullName, email) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassWord());
            statement.setString(3, user.getFullName());
            statement.setString(4, user.getEmail());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
