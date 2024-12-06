package com.example.todolist.dao;
import com.example.todolist.model.Comment;
import com.example.todolist.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

/**
 * Provides Data Access Object (DAO) methods for interacting with comments in the database.
 * This class allows adding, retrieving, editing, and deleting comments associated with tasks.
 *
 * @author Meftah Mohamed
 */
public class CommentDAO {

    /**
     * Adds a new comment to a task.
     *
     * @param taskId The ID of the task to which the comment will be added.
     * @param commentText The text content of the comment.
     * @return {@code true} if the comment was successfully added, {@code false} otherwise.
     */
    public boolean addComment(int taskId, String commentText) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sqlQuery = "INSERT INTO comments (taskId, text) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, taskId);
            statement.setString(2, commentText);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all comments associated with a specific task.
     *
     * @param taskId The ID of the task for which comments are to be retrieved.
     * @return A list of comments associated with the specified task.
     */
    public ArrayList<Comment> getCommentsByTaskId(int taskId) {
        ArrayList<Comment> comments = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sqlQuery = "SELECT * FROM comments WHERE taskId = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, taskId);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Comment comment = new Comment(
                        result.getInt("id"),
                        result.getString("text"),
                        result.getTimestamp("creationDate").toLocalDateTime()
                );
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    /**
     * Deletes a comment from the database.
     *
     * @param commentId The ID of the comment to be deleted.
     * @return {@code true} if the comment was successfully deleted, {@code false} otherwise.
     */
    public boolean deleteComment(int commentId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sqlQuery = "DELETE FROM comments WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, commentId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Edits an existing comment's text.
     *
     * @param commentId The ID of the comment to be edited.
     * @param newText The new text for the comment.
     * @return {@code true} if the comment was successfully updated, {@code false} otherwise.
     */
    public boolean editComment(int commentId, String newText) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sqlQuery = "UPDATE comments SET text = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, newText);
            statement.setInt(2, commentId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
