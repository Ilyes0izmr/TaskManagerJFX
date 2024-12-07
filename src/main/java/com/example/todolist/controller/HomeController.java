package com.example.todolist.controller;

import com.example.todolist.controller.LoginController;
import com.example.todolist.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.todolist.dao.TaskDAO;
import java.time.LocalDate;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class HomeController {

    @FXML
    private ListView<TaskListImpl> categoryList;

    @FXML
    private ListView<TaskListImpl> collabList;

    @FXML
    private ListView<TaskImpl> taskList;
    private TaskListImpl taskListModel ;

    private TaskDAO taskDAO;

    public HomeController() {
        this.taskListModel = new TaskListImpl("general" , User.getUserName()) ;
        this.taskDAO = new TaskDAO();
    }

    public void initialize() {
        try {
            ObservableList<TaskImpl> tasks = FXCollections.observableArrayList(taskDAO.getTasksByUserName(User.getUserName()));
            taskList.getItems().clear();
            taskListModel.getTasks().addAll(tasks);
            taskList.setItems(taskListModel.getTasks());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error during initialization: " + e.getMessage());
        }
    }

    public void handleAddTaskPopup() {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/todolist/view/fxml/addTaskPopup.fxml"));
            AnchorPane popupRoot = loader.load();
            // Get the controller for the popup
            addTaskController controller = loader.getController();
            // Create a new stage for the popup
            Stage popupStage = new Stage();
            popupStage.setTitle("Add Task");
            popupStage.initModality(Modality.APPLICATION_MODAL); // Block interaction with the main window
            popupStage.setResizable(false);
            popupStage.setScene(new Scene(popupRoot));
            // Show the popup
            popupStage.showAndWait();
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading Add Task Popup.");
        }
    }
}