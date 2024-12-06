package com.example.todolist.dao;

import com.example.todolist.model.User;
import com.example.todolist.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public boolean login(String userName , String passWord){
        try(Connection connection = DatabaseConnection.getConnection()){
            String sqlQuery = "SELECT * FROM users WHERE username = ? AND password = ?" ;
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1,userName);
            statement.setString(2,passWord);
            ResultSet result = statement.executeQuery();
            return result.next() ;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("error table");
            return false ;
        }
    }

    public boolean signUp(User user) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sqlQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
            System.out.println("User has been added!");
            return true;
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) { // SQLState for unique constraint violation
                System.out.println("Username already exists.");
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }


}
