package com.example.todolist.controller;

import com.example.todolist.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.example.todolist.dao.TaskDAO;

import java.io.IOException;

public class HomeController {

    @FXML
    private ListView<TaskListImpl> categoryList;

    @FXML
    private ListView<TaskListImpl> collabList;

    @FXML
    private ListView<TaskImpl> taskList;

    private TaskListImpl taskListModel = new TaskListImpl("general", User.getUserName());
    private TaskDAO taskDAO = new TaskDAO();

    // Optional singleton pattern
    private static HomeController instance;

    public HomeController() { }

    public static HomeController getInstance() {
        return instance;  // Just return the already created instance, if it exists
    }

    public void setInstance(HomeController instance) {
        HomeController.instance = instance; // Set the singleton instance explicitly
    }

    public void refresh() {
        try {
            taskList.getItems().clear();
            ObservableList<TaskImpl> tasks = FXCollections.observableArrayList(taskDAO.getTasksByUserName(User.getUserName()));
            taskListModel.getTasks().setAll(tasks);
            taskList.setItems(taskListModel.getTasks());
            taskList.setCellFactory(param -> new TaskCell());
            System.out.println("Page refreshed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error during refresh: " + e.getMessage());
        }
    }

    public void initialize() {
        // If singleton is needed, ensure it's initialized here
        if (instance == null) {
            instance = this;
        }
        refresh();
    }

    public void handleAddTaskPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/todolist/view/fxml/addTaskPopup.fxml"));
            AnchorPane popupRoot = loader.load();
            addTaskController controller = loader.getController();
            Stage popupStage = new Stage();
            popupStage.setTitle("Add Task");
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setResizable(false);
            popupStage.setScene(new Scene(popupRoot));
            popupStage.showAndWait();
            refresh();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading Add Task Popup.");
        }
    }
}
