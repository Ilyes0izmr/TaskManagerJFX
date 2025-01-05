package com.example.todolist.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @brief Manages the connection to the MySQL database.
 * This class provides methods to establish and close a connection to the MySQL database.
 * It uses the Singleton pattern to ensure only one connection instance is created and reused.
 *
 * @see java.sql.Connection For the Connection interface used in this class.
 * @see java.sql.DriverManager For the DriverManager class used to establish the connection.
 * @see java.sql.SQLException For exceptions related to database operations.
 *
 * @author Izemmouren Ilyes
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/todolist";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;
    private DatabaseConnection() {}

    /**
     * @brief Retrieves the database connection
     * This method establishes a connection to the MySQL database if one does not already exist
     * or if the existing connection is closed. It uses the DriverManager to create the connection.
     *
     * @return Connection The active database connection.
     * @throws SQLException If an error occurs while establishing the connection.
     *
     * @author Izemmouren Ilyes
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("\nConnected to the database successfully!");
            } catch (SQLException e) {
                System.out.println("\nConnection Error !!");
                throw e;
            }
        }
        return connection;
    }

    /**
     * @brief Closes the database connection
     * This method closes the active database connection if it is not already closed.
     * It ensures that resources are properly released.
     *
     * @throws SQLException If an error occurs while closing the connection.
     *
     * @author Ilyes Izemmouren
     */
    public static void closeConnection() throws SQLException {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("\nDatabase connection closed successfully!");
            } catch (SQLException e) {
                System.out.println("\nFailed to close the database !!");
            }
        }
    }
}