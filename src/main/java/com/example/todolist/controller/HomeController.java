package com.example.todolist.controller;

import com.example.todolist.dao.CategoryDAO;
import javafx.scene.control.CheckMenuItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.example.todolist.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.example.todolist.dao.TaskDAO;
import java.io.IOException;
import java.util.Comparator;

public class HomeController {
    @FXML
    private CheckMenuItem checkMenuItemCompleted;

    @FXML
    private CheckMenuItem checkMenuItemInProgress;

    @FXML
    private CheckMenuItem checkMenuItemAbandoned;

    @FXML
    private CheckMenuItem checkMenuItemPending;

    @FXML
    private CheckMenuItem checkMenuItemHigh;
    @FXML
    private CheckMenuItem checkMenuItemMedium;
    @FXML
    private CheckMenuItem checkMenuItemLow;

    @FXML
    private ListView<Category> categoryList;

    @FXML
    private ListView<Category> collabList;

    @FXML
    private ListView<TaskImpl> taskList;



    private TaskListImpl taskListModel = new TaskListImpl(null, User.getUserName());
    private TaskDAO taskDAO = new TaskDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();
    private String currentCategoryName;

    // Optional singleton pattern
    private static HomeController instance;

    public HomeController() { }

    public static HomeController getInstance() {
        return instance;  // Just return the already created instance, if it exists
    }

    public void setInstance(HomeController instance) {
        HomeController.instance = instance; // Set the singleton instance explicitly
    }

    public void refreshCategories() {
        try {
            ObservableList<Category> categories = FXCollections.observableArrayList(categoryDAO.getAllCategories(User.getUserName()));
            categoryList.setItems(categories);
            categoryList.setCellFactory(param -> new CategoryCell());
            System.out.println("Category list refreshed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error refreshing categories: " + e.getMessage());
        }
    }



    public void refresh() {
        try {
            taskList.getItems().clear();
            ObservableList<TaskImpl> tasks;
            if(currentCategoryName == null) {
                tasks = FXCollections.observableArrayList(taskDAO.getTasksByUserName(User.getUserName()));
            }else {
                tasks = FXCollections.observableArrayList(taskDAO.getTasksByCategory(currentCategoryName));
            }
            taskListModel.getTasks().setAll(tasks);
            taskListModel.setName(currentCategoryName);
            taskList.setItems(taskListModel.getTasks());
            taskList.setCellFactory(param -> new TaskCell());
            System.out.println("Page refreshed successfully.");

            // Uncheck all status filter checkboxes
            checkMenuItemCompleted.setSelected(false);
            checkMenuItemInProgress.setSelected(false);
            checkMenuItemAbandoned.setSelected(false);
            checkMenuItemPending.setSelected(false);

            // Uncheck all priority filter checkboxes
            checkMenuItemHigh.setSelected(false);
            checkMenuItemMedium.setSelected(false);
            checkMenuItemLow.setSelected(false);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error during refresh: " + e.getMessage());
        }
    }



    public void initialize() {
        categoryList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                handleCategoryDoubleClick();
            }
        });
        if (instance == null) {
            instance = this;
        }
        currentCategoryName = null;
        refresh();
        refreshCategories(); // Refresh categories as well
    }

    private void handleCategoryDoubleClick() {
        Category selectedCategory = categoryList.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            currentCategoryName = selectedCategory.getName(); // Assuming Category has a getName() method
            System.out.println("Current category set to: " + currentCategoryName);
            refresh();
        } else {
            System.out.println("No category selected.");
        }
    }

    @FXML
    private void handleHomeButton(){
        currentCategoryName = null;
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

    @FXML
    public void handleAddCategoryPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/todolist/view/fxml/AddCategoryPopUp.fxml"));

            AnchorPane popupRoot = loader.load();

            // Get the controller to pass data or initialize if necessary
            AddCategoryController controller = loader.getController();

            // Create a new stage for the popup
            Stage popupStage = new Stage();
            popupStage.setTitle("Add New Category");
            popupStage.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows
            popupStage.setResizable(false); // Disable resizing
            popupStage.setScene(new Scene(popupRoot));
            popupStage.showAndWait(); // Wait until the popup is closed

            // Refresh the categories list after closing the popup
            refreshCategories();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading Add Category Popup.");
        }
    }



    @FXML
    private void handleSortFarthest() {
        try {
            ObservableList<TaskImpl> tasks = FXCollections.observableArrayList(taskDAO.getTasksByUserName(User.getUserName()));
            FXCollections.sort(tasks, Comparator.comparing(TaskImpl::getDueDate).reversed()); // Sort by farthest date
            taskList.setItems(tasks);
            taskList.setCellFactory(param -> new TaskCell());
            System.out.println("Tasks sorted by farthest due date.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error during sorting by farthest due date: " + e.getMessage());
        }
    }

    @FXML
    private void handleSortClosest() {
        try {
            ObservableList<TaskImpl> tasks = FXCollections.observableArrayList(taskDAO.getTasksByUserName(User.getUserName()));
            FXCollections.sort(tasks, Comparator.comparing(TaskImpl::getDueDate)); // Sort by closest due date
            taskList.setItems(tasks);
            taskList.setCellFactory(param -> new TaskCell());
            System.out.println("Tasks sorted by closest due date.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error during sorting by closest due date: " + e.getMessage());
        }
    }

    @FXML
    private void handleSortHighest() {
        try {
            ObservableList<TaskImpl> tasks = FXCollections.observableArrayList(taskDAO.getTasksByUserName(User.getUserName()));
            FXCollections.sort(tasks, Comparator.comparing(TaskImpl::getPriority).reversed()); // Sort by highest priority
            taskList.setItems(tasks);
            taskList.setCellFactory(param -> new TaskCell());
            System.out.println("Tasks sorted by highest priority.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error during sorting by highest priority: " + e.getMessage());
        }
    }

    @FXML
    private void handleSortLowest() {
        try {
            ObservableList<TaskImpl> tasks = FXCollections.observableArrayList(taskDAO.getTasksByUserName(User.getUserName()));
            FXCollections.sort(tasks, Comparator.comparing(TaskImpl::getPriority)); // Sort by lowest priority
            taskList.setItems(tasks);
            taskList.setCellFactory(param -> new TaskCell());
            System.out.println("Tasks sorted by lowest priority.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error during sorting by lowest priority: " + e.getMessage());
        }
    }

    @FXML
    private void handleFilterStatus() {
        try {
            ObservableList<TaskImpl> tasks = FXCollections.observableArrayList(taskDAO.getTasksByUserName(User.getUserName()));

            // Get selected statuses
            boolean completedSelected = checkMenuItemCompleted.isSelected();
            boolean inProgressSelected = checkMenuItemInProgress.isSelected();
            boolean abandonedSelected = checkMenuItemAbandoned.isSelected();
            boolean pendingSelected = checkMenuItemPending.isSelected();

            // Filter tasks based on selected statuses
            if (!completedSelected) {
                tasks.removeIf(task -> task.getStatus() == Status.COMPLETED);
            }
            if (!inProgressSelected) {
                tasks.removeIf(task -> task.getStatus() == Status.IN_PROGRESS);
            }
            if (!abandonedSelected) {
                tasks.removeIf(task -> task.getStatus() == Status.ABANDONED);
            }
            if (!pendingSelected) {
                tasks.removeIf(task -> task.getStatus() == Status.PENDING);
            }

            // Set the filtered tasks into the task list
            taskList.setItems(tasks);
            taskList.setCellFactory(param -> new TaskCell());
            System.out.println("Tasks filtered by selected status.");

            // If all checkboxes are unchecked, call refresh
            if (!completedSelected && !inProgressSelected && !abandonedSelected && !pendingSelected) {
                try {
                    taskList.getItems().clear();
                    tasks = FXCollections.observableArrayList(taskDAO.getTasksByUserName(User.getUserName()));
                    taskListModel.getTasks().setAll(tasks);
                    taskList.setItems(taskListModel.getTasks());
                    taskList.setCellFactory(param -> new TaskCell());
                    System.out.println("Page refreshed successfully.");

                    // Uncheck all status filter checkboxes
                    checkMenuItemCompleted.setSelected(false);
                    checkMenuItemInProgress.setSelected(false);
                    checkMenuItemAbandoned.setSelected(false);
                    checkMenuItemPending.setSelected(false);

                    System.out.println("All checkboxes unchecked.");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error during refresh: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error during filtering by status: " + e.getMessage());
        }
    }

    @FXML
    private void handleFilterPriority() {
        try {
            ObservableList<TaskImpl> tasks = FXCollections.observableArrayList(taskDAO.getTasksByUserName(User.getUserName()));

            // Get selected priorities
            boolean highSelected = checkMenuItemHigh.isSelected();
            boolean mediumSelected = checkMenuItemMedium.isSelected();
            boolean lowSelected = checkMenuItemLow.isSelected();

            // Filter tasks based on selected priorities
            if (!highSelected) {
                tasks.removeIf(task -> task.getPriority() == Priority.HIGH);
            }
            if (!mediumSelected) {
                tasks.removeIf(task -> task.getPriority() == Priority.MEDIUM);
            }
            if (!lowSelected) {
                tasks.removeIf(task -> task.getPriority() == Priority.LOW);
            }

            // Set the filtered tasks into the task list
            taskList.setItems(tasks);
            taskList.setCellFactory(param -> new TaskCell());
            System.out.println("Tasks filtered by selected priority.");

            // If all checkboxes are unchecked, call refresh
            if (!highSelected && !mediumSelected && !lowSelected) {
                try {
                    taskList.getItems().clear();
                    tasks = FXCollections.observableArrayList(taskDAO.getTasksByUserName(User.getUserName()));
                    taskListModel.getTasks().setAll(tasks);
                    taskList.setItems(taskListModel.getTasks());
                    taskList.setCellFactory(param -> new TaskCell());
                    System.out.println("Page refreshed successfully.");

                    // Uncheck all priority filter checkboxes
                    checkMenuItemHigh.setSelected(false);
                    checkMenuItemMedium.setSelected(false);
                    checkMenuItemLow.setSelected(false);

                    System.out.println("All checkboxes unchecked.");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error during refresh: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error during filtering by priority: " + e.getMessage());
        }
    }



}
