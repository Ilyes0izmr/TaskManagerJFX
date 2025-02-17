package com.example.todolist.controller;

import com.example.todolist.dao.CategoryDAO;
import com.example.todolist.dao.TaskDAO;
import com.example.todolist.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class EditTaskController {

    @FXML
    private TextField taskFieldTitle;

    @FXML
    private ComboBox<Status> taskStatusComboBox;

    @FXML
    private TextField taskFieldDescription;

    @FXML
    private DatePicker taskFieldDueDate;

    @FXML
    private ComboBox<Reminder> taskReminderComboBox;

    @FXML
    private ComboBox<Priority> taskPriorityComboBox;

    @FXML
    private ComboBox<String> taskCategoryComboBox;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private TaskImpl task;

    public void setTask(TaskImpl task) {
        this.task = task;
        loadTaskData();
    }

    public void initialize() {
        ObservableList<Priority> priorities = FXCollections.observableArrayList();
        priorities.addAll(Priority.values());
        taskPriorityComboBox.setItems(priorities);

        ObservableList<Reminder> reminders = FXCollections.observableArrayList();
        reminders.addAll(Reminder.values());
        taskReminderComboBox.setItems(reminders);

        ObservableList<String> categories = FXCollections.observableArrayList();
        CategoryDAO categoryDAO = new CategoryDAO();
        ArrayList<Category> categoryList = categoryDAO.getAllCategories(User.getUserName());

        ObservableList<Status> status = FXCollections.observableArrayList();
        status.addAll(Status.values());
        taskStatusComboBox.setItems(status);

        // Transform Category objects to their names and add them to the ObservableList
        for (Category category : categoryList) {
            categories.add(category.getName());
        }

        taskCategoryComboBox.setItems(categories);
    }

    private void loadTaskData() {
        // Load existing task data into the input fields
        taskFieldTitle.setText(task.getTitle());
        taskFieldDescription.setText(task.getDescription());
        taskFieldDueDate.setValue(task.getDueDate());
        taskPriorityComboBox.setValue(task.getPriority());
        taskStatusComboBox.setValue(task.getStatus());
        taskReminderComboBox.setValue(task.getReminder());
        taskCategoryComboBox.setValue(task.getCategoryName());
    }

    @FXML
    private void handleSaveTask() {
        System.out.println("Save button clicked"); // Debug log

        // Get the updated task data from the fields
        String title = taskFieldTitle.getText();
        String description = taskFieldDescription.getText();
        Priority priority = taskPriorityComboBox.getValue();
        String dueDate = taskFieldDueDate.getValue().toString();
        Status status = taskStatusComboBox.getValue();
        Reminder reminder = taskReminderComboBox.getValue();
        String category = taskCategoryComboBox.getValue();

        // Update the task object with the new data
        task.setTitle(title);
        task.setDescription(description);
        task.setPriority(priority);
        task.setDueDate(java.time.LocalDate.parse(dueDate));
        task.setReminder(reminder);
        task.setCategoryName(category);
        task.setStatus(status);

        // Save the updated task using DAO
        TaskDAO taskDAO = new TaskDAO();
        taskDAO.editTask(task);

        // Close the Edit Task window
        taskFieldTitle.getScene().getWindow().hide();

        System.out.println("Task saved and window closed");
    }

    @FXML
    private void handleCancel() {
        System.out.println("Cancel button clicked"); // Debug log

        // Close the Edit Task window without saving changes
        taskFieldTitle.getScene().getWindow().hide();

        System.out.println("Window closed without saving");
    }
}
