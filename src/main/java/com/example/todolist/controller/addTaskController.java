package com.example.todolist.controller;

import com.example.todolist.dao.CategoryDAO;
import com.example.todolist.dao.TaskDAO;
import com.example.todolist.model.*;
import com.example.todolist.ui.NotificationAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;


import java.time.LocalDate;
import java.util.ArrayList;

public class addTaskController {
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

    @FXML
    private ComboBox<String> taskCategoryComboBox;

    public void initialize(){
        ObservableList<Priority> priorities = FXCollections.observableArrayList();
        priorities.addAll(Priority.values());
        taskPriorityComboBox.setItems(priorities);

        ObservableList<Reminder> reminders = FXCollections.observableArrayList();
        reminders.addAll(Reminder.values());
        taskReminderComboBox.setItems(reminders);

        ObservableList<String> categories = FXCollections.observableArrayList();
        CategoryDAO categoryDAO = new CategoryDAO();
        ArrayList<Category> categoryList = categoryDAO.getAllCategories(User.getUserName());

        // Transform Category objects to their names and add them to the ObservableList
        for (Category category : categoryList) {
            categories.add(category.getName());
        }

        taskCategoryComboBox.setItems(categories);

    }


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

        //TODO : error handling here
        if(taskTitle != null && !taskTitle.isEmpty() && deuDate != null){
            insertTask(taskTitle , taskDescription , status , deuDate , creationDate , priority , reminder ,categoryName,userName);
            System.out.println("task added successfully");
            taskFieldTitle.getScene().getWindow().hide();
            NotificationAlert.showAlert("","Task added successfully");
        }else{
            System.out.println("task title / due date is empty ");
            NotificationAlert.showAlert("","Task addition failed");
        }
    }

    public void insertTask(String taskTitle ,String taskDescription,Status status , LocalDate deuDate , LocalDate creationDate ,Priority priority , Reminder reminder , String categoryName, String userName){
        TaskImpl task = new TaskImpl(11,taskTitle,taskDescription,status,deuDate,creationDate,priority,null,reminder,categoryName,userName) ;
        TaskDAO.getInstance().addTask(task);
    }
}
