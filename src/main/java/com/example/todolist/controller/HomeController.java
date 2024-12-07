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


    public void handleAddTask(){
        String taskTitle = taskFieldTitle.getText();
        String taskDescription = taskFieldDescription.getText();
        Status status = Status.PENDING ;
        LocalDate deuDate = taskFieldDueDate.getValue();
        LocalDate creationDate = LocalDate.now();
        Priority priority = taskPriorityComboBox.getValue();
        Reminder reminder = taskReminderComboBox.getValue();
        String categoryName = taskCategoryComboBox.getValue();

    }



}
