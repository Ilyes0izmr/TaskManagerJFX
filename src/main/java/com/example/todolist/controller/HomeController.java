package com.example.todolist.controller;

import com.example.todolist.controller.LoginController;
import com.example.todolist.model.Priority;
import com.example.todolist.model.Reminder;
import com.example.todolist.model.Status;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;



public class HomeController {

    @FXML
    private ComboBox<String> taskCategoryComboBox;

    @FXML
    private TextField taskFieldDescription;

    @FXML
    private DatePicker taskFieldDueDate;

    @FXML
    private TextField taskFieldTitle;

    @FXML
    private ComboBox<Priority> taskPriorityComboBox;

    @FXML
    private ComboBox<Reminder> taskReminderComboBox;


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
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading Add Task Popup.");
        }
    }



}
