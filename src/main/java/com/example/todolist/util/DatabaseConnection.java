package com.example.todolist.util;

import java.net.http.HttpConnectTimeoutException;
import java.sql.Connection ;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/todolist" ;
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    private DatabaseConnection() {}

    /**
     *
     * @brief  this is a function to get the Data Base connection
     *
     * @return Connection
     *
     * @throws SQLException
     *
     * @author Izemmouren Ilyes
     */
    public static Connection getConnection() throws SQLException {
        if(connection == null || connection.isClosed()){
            try{
                connection = DriverManager.getConnection(URL , USER , PASSWORD ) ;
                System.out.println("Connected to the database successfully!");
            }catch (SQLException e){
                System.out.println("Connection Error");
                throw e ;
            }
        }
        return connection ;
    }

    /**
     *
     * @brief this methode close the connection with the data base
     *
     * @throws SQLException
     *
     * @author Ilyes Izemmouren
     */
    public static void closeConnection() throws SQLException {
        if(connection != null){
            try{
                connection.close() ;
                System.out.println("Database connection closed successfully!");
            }catch(SQLException e){
                System.out.println("Failed to close the data base !!");
            }
        }
    }
}
