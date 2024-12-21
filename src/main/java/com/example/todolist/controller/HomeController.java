package com.example.todolist.controller;

import com.example.todolist.dao.CategoryDAO;
import com.example.todolist.dao.CollabCategoryDAO;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.CheckMenuItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.example.todolist.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.example.todolist.dao.TaskDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    @FXML
    private TextField categorySearch ;

    @FXML
    private TextField taskSearch;


    private TaskListImpl taskListModel = new TaskListImpl(null, User.getUserName());
    private TaskDAO taskDAO = new TaskDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();
    private CollabCategoryDAO collabCategoryDAO = new CollabCategoryDAO();
    private String currentCategoryName;
    private String collaberatorName;


    // Optional singleton pattern
    private static HomeController instance;

    public HomeController() { }

    public static HomeController getInstance() {
        return instance;  // Just return the already created instance, if it exists
    }

    public void setInstance(HomeController instance) {
        HomeController.instance = instance; // Set the singleton instance explicitly
    }

    public void initialize() {
        categoryList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                handleCategoryDoubleClick();
            }
        });

        collabList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                handleCollabDoubleClick();
            }
        });

        if (instance == null) {
            instance = this;
        }
        currentCategoryName = null;
        collaberatorName = null;
        refresh();
        refreshCategories();
        refreshCollabCategories();

        categorySearch.textProperty().addListener((observable, oldValue, newValue) -> {
            handleSearchCategory(newValue);
        });

        taskSearch.textProperty().addListener((obs, oldValue, newValue) -> {
            handleSearch(newValue); // Call handleSearch with the current search text
        });
    }

    public void refresh() {
        try {
            taskList.getItems().clear();
            ObservableList<TaskImpl> tasks;
            if(currentCategoryName == null) {
                tasks = FXCollections.observableArrayList(taskDAO.getTasksByUserName(User.getUserName()));
            }else {
                if(collaberatorName == null) {
                    tasks = FXCollections.observableArrayList(taskDAO.getTasksByCategory(currentCategoryName, User.getUserName()));
                }else{
                    tasks = FXCollections.observableArrayList(taskDAO.getTasksByCategory(currentCategoryName, collaberatorName));
                }
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

    public void refreshCollabCategories() {
        try {
            ObservableList<Category> categories = FXCollections.observableArrayList(collabCategoryDAO.getAllCollabCategories(User.getUserName()));
            collabList.setItems(categories);
            collabList.setCellFactory(param -> new CollabCategoryCell());
            System.out.println("Collab category list refreshed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error refreshing collab categories: " + e.getMessage());
        }
    }

    private void handleCategoryDoubleClick() {
        Category selectedCategory = categoryList.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            currentCategoryName = selectedCategory.getName();
            collaberatorName = null;
            System.out.println("Current category set to: " + currentCategoryName);
            refresh();
        } else {
            System.out.println("No category selected.");
        }
    }

    private void handleCollabDoubleClick() {
        Category selectedCategory = collabList.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            currentCategoryName = selectedCategory.getName();
            collaberatorName = selectedCategory.getUserName();
            System.out.println("Current category set to: " + currentCategoryName);
            refresh();
        } else {
            System.out.println("No category selected.");
        }
    }

    @FXML
    private void handleHomeButton(){
        currentCategoryName = null;
        collaberatorName = null;
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
    public void showNewCollabPopUp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/todolist/view/fxml/newCollabPopUp.fxml"));
            AnchorPane popupRoot = loader.load();

            NewCollabController controller = loader.getController();
            Stage popupStage = new Stage();
            popupStage.setTitle("New Collab");
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setResizable(false);
            popupStage.setScene(new Scene(popupRoot));
            popupStage.showAndWait();
            refreshCollabCategories();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteCollabCategory(Category category) {
        try {
            // Call DAO to remove the category
            collabCategoryDAO.deleteCollabCategory(category);

            // Refresh the collab categories list
            refreshCollabCategories();

            System.out.println("Deleted collab category: " + category.getName());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error deleting collab category: " + e.getMessage());
        }
    }


    @FXML
    private void handleSortFarthest() {
        try {
            ObservableList<TaskImpl> tasks;
            if(currentCategoryName == null) {
                tasks = FXCollections.observableArrayList(taskDAO.getTasksByUserName(User.getUserName()));
            }else {
                if(collaberatorName == null) {
                    tasks = FXCollections.observableArrayList(taskDAO.getTasksByCategory(currentCategoryName, User.getUserName()));
                }else{
                    tasks = FXCollections.observableArrayList(taskDAO.getTasksByCategory(currentCategoryName, collaberatorName));
                }
            }
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
            ObservableList<TaskImpl> tasks;
            if(currentCategoryName == null) {
                tasks = FXCollections.observableArrayList(taskDAO.getTasksByUserName(User.getUserName()));
            }else {
                if(collaberatorName == null) {
                    tasks = FXCollections.observableArrayList(taskDAO.getTasksByCategory(currentCategoryName, User.getUserName()));
                }else{
                    tasks = FXCollections.observableArrayList(taskDAO.getTasksByCategory(currentCategoryName, collaberatorName));
                }
            }
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
            ObservableList<TaskImpl> tasks;
            if(currentCategoryName == null) {
                tasks = FXCollections.observableArrayList(taskDAO.getTasksByUserName(User.getUserName()));
            }else {
                if(collaberatorName == null) {
                    tasks = FXCollections.observableArrayList(taskDAO.getTasksByCategory(currentCategoryName, User.getUserName()));
                }else{
                    tasks = FXCollections.observableArrayList(taskDAO.getTasksByCategory(currentCategoryName, collaberatorName));
                }
            }
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
            ObservableList<TaskImpl> tasks;
            if(currentCategoryName == null) {
                tasks = FXCollections.observableArrayList(taskDAO.getTasksByUserName(User.getUserName()));
            }else {
                if(collaberatorName == null) {
                    tasks = FXCollections.observableArrayList(taskDAO.getTasksByCategory(currentCategoryName, User.getUserName()));
                }else{
                    tasks = FXCollections.observableArrayList(taskDAO.getTasksByCategory(currentCategoryName, collaberatorName));
                }
            }
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
            ObservableList<TaskImpl> tasks;
            if(currentCategoryName == null) {
                tasks = FXCollections.observableArrayList(taskDAO.getTasksByUserName(User.getUserName()));
            }else {
                if(collaberatorName == null) {
                    tasks = FXCollections.observableArrayList(taskDAO.getTasksByCategory(currentCategoryName, User.getUserName()));
                }else{
                    tasks = FXCollections.observableArrayList(taskDAO.getTasksByCategory(currentCategoryName, collaberatorName));
                }
            }
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
                    if(currentCategoryName == null) {
                        tasks = FXCollections.observableArrayList(taskDAO.getTasksByUserName(User.getUserName()));
                    }else {
                        if(collaberatorName == null) {
                            tasks = FXCollections.observableArrayList(taskDAO.getTasksByCategory(currentCategoryName, User.getUserName()));
                        }else{
                            tasks = FXCollections.observableArrayList(taskDAO.getTasksByCategory(currentCategoryName, collaberatorName));
                        }
                    }
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
            ObservableList<TaskImpl> tasks;
            if(currentCategoryName == null) {
                tasks = FXCollections.observableArrayList(taskDAO.getTasksByUserName(User.getUserName()));
            }else {
                if(collaberatorName == null) {
                    tasks = FXCollections.observableArrayList(taskDAO.getTasksByCategory(currentCategoryName, User.getUserName()));
                }else{
                    tasks = FXCollections.observableArrayList(taskDAO.getTasksByCategory(currentCategoryName, collaberatorName));
                }
            }

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
                    if(currentCategoryName == null) {
                        tasks = FXCollections.observableArrayList(taskDAO.getTasksByUserName(User.getUserName()));
                    }else {
                        if(collaberatorName == null) {
                            tasks = FXCollections.observableArrayList(taskDAO.getTasksByCategory(currentCategoryName, User.getUserName()));
                        }else{
                            tasks = FXCollections.observableArrayList(taskDAO.getTasksByCategory(currentCategoryName, collaberatorName));
                        }
                    }
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

    private void handleSearch(String searchText) {
        // Call the DAO method to search for tasks by title
        List<TaskImpl> searchResults = taskDAO.searchTasksByTitle(searchText,User.getUserName(),currentCategoryName);
        if(collaberatorName == null) {
            searchResults = taskDAO.searchTasksByTitle(searchText, User.getUserName(), currentCategoryName);
        }else{
            searchResults = taskDAO.searchTasksByTitle(searchText, collaberatorName, currentCategoryName);
        }

        // Update the ListView with the search results
        ObservableList<TaskImpl> observableResults = FXCollections.observableArrayList(searchResults);
        taskList.setItems(observableResults);
    }

    private void handleSearchCategory(String searchText) {
        ArrayList<Category> searchResults = categoryDAO.searchCategoriesByKeyword(searchText,User.getUserName());
        ObservableList<Category> observableResults = FXCollections.observableArrayList(searchResults);
        categoryList.setItems(observableResults);
    }

    public void handleSettings(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/todolist/view/fxml/SettingsView.fxml"));
        Parent root = loader.load();
        // Get the current stage (window)
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        // Create a new scene with the loaded FXML content
        Scene scene = new Scene(root);
        // Set the new scene to the current stage
        currentStage.setScene(scene);
        currentStage.show();
    }
}
