package com.example.todolist.controller;

import com.example.todolist.dao.TaskDAO;
import com.example.todolist.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;


import java.time.LocalDate;

public class addTaskController {

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
        String userName = User.getUserName();  // Get the static userName

        if(taskTitle != null && !taskTitle.isEmpty() && deuDate != null){
            insertTask(taskTitle , taskDescription , status , deuDate , creationDate , priority , reminder ,categoryName,userName);
        }else{
            System.out.println("task title / due date is empty ");
        }
    }

    public void insertTask(String taskTitle ,String taskDescription,Status status , LocalDate deuDate , LocalDate creationDate ,Priority priority , Reminder reminder , String categoryName, String userName){
        TaskImpl task = new TaskImpl(taskTitle,taskDescription,status,deuDate,creationDate,priority,null,reminder,categoryName,userName) ;
        TaskDAO.getInstance().addTask(task);
    }
}
