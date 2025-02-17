package com.example.todolist.controller;
import com.example.todolist.dao.CategoryDAO;
import com.example.todolist.dao.CollabCategoryDAO;
import com.example.todolist.dao.NotificationDAO;
import com.example.todolist.ui.NotificationAlert;
import com.example.todolist.ui.RandomColor;
import com.example.todolist.util.TaskImplSerializer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.CheckMenuItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.todolist.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.example.todolist.dao.TaskDAO;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javafx.stage.FileChooser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.util.Duration;

import java.lang.reflect.Type;


public class HomeController {
    @FXML
    private Label timeLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label clockLabel;
    @FXML
    private Label categoryNameLabel;


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

    @FXML
    private Label userNameName;

    @FXML
    private Label firstLetters;


    private TaskListImpl taskListModel = new TaskListImpl(null, User.getUserName());
    private TaskDAO taskDAO = new TaskDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();
    private CollabCategoryDAO collabCategoryDAO = new CollabCategoryDAO();
    private String currentCategoryName;
    private String collaberatorName;
    private boolean fullAccess;
    private NotificationDAO notificationDAO = new NotificationDAO();

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
        usernameLabel.setText(User.getUserName()+" !");
        userNameName.setText(User.getUserName());
        firstLetters.setText(User.getUserName().substring(0, 2).toUpperCase());
        String randomColor = RandomColor.getRandomColor();
        firstLetters.setStyle("-fx-text-fill: " + randomColor + ";");
        categoryNameLabel.setText("  >> Home");
        updateTime();
        setGreetingMessage();
        updateTime();
        // Create a Timeline that updates the time every second
        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTime()));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();

        categoryList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String name = handleCategoryDoubleClick();
                categoryNameLabel.setText("  >> "+name+" page");
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
        fullAccess = true;
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
            taskList.setCellFactory(param -> new TaskCell(fullAccess));
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
            categoryList.getItems().clear();
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
            collabList.getItems().clear();
            ObservableList<Category> categories = FXCollections.observableArrayList(collabCategoryDAO.getAllCollabCategories(User.getUserName()));
            collabList.setItems(categories);
            collabList.setCellFactory(param -> new CollabCategoryCell());
            System.out.println("Collab category list refreshed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error refreshing collab categories: " + e.getMessage());
        }
    }

    private void setGreetingMessage() {
        int hour = LocalDateTime.now().getHour();
        if (hour < 12) {
            welcomeLabel.setText("Good Morning");
        } else if (hour < 18) {
            welcomeLabel.setText("Good Afternoon");
        } else {
            welcomeLabel.setText("Good Evening");
        }
    }
    private void updateTime() {
        // Format to include the day and the time (without seconds)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EE, MM/dd/yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss");
        timeLabel.setText(LocalDateTime.now().format(formatter));
        clockLabel.setText(LocalDateTime.now().format(formatter2));
    }


    private String handleCategoryDoubleClick() {
        Category selectedCategory = categoryList.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            currentCategoryName = selectedCategory.getName();
            collaberatorName = null;
            fullAccess = true;
            System.out.println("Current category set to: " + currentCategoryName);
            refresh();
            return currentCategoryName ;
        } else {
            System.out.println("No category selected.");
            return "Home" ;
        }
    }

    private void handleCollabDoubleClick() {
        Category selectedCategory = collabList.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            currentCategoryName = selectedCategory.getName();
            collaberatorName = selectedCategory.getUserName();
            fullAccess = selectedCategory.isFullAccess();
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
        fullAccess = true;
        categoryNameLabel.setText("  >> Home");
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
            NotificationAlert.showAlert("Success", "category added successfully");
            // Refresh the categories list after closing the popup
            refreshCategories();
        } catch (IOException e) {
            e.printStackTrace();
            NotificationAlert.showAlert("Failed", "category can not be added");
            System.out.println("Error loading Add Category Popup.");
        }
    }

    /**
     * @NOTE : done
     */
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
            // Create a notification for successful deletion
            Notification successNotification = new Notification(
                    "Collaboration Deleted",
                    "The collaboration category '" + category.getName() + "' has been successfully deleted.",
                    NotifType.CATEGORY, // Assuming SYSTEM is the type for system notifications
                    LocalDate.now() // Current date
            );
            // Save the notification to the database
            notificationDAO.addNotification(successNotification, User.getUserName()); // Replace "current_user" with the actual username
            NotificationAlert.showAlert("Success", "Collaboration Deleted");

        } catch (Exception e) {
            e.printStackTrace();
            NotificationAlert.showAlert("Error", "Collaboration Can't Be Deleted");
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
            taskList.setCellFactory(param -> new TaskCell(fullAccess));
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
            taskList.setCellFactory(param -> new TaskCell(fullAccess));
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
            taskList.setCellFactory(param -> new TaskCell(fullAccess));
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
            taskList.setCellFactory(param -> new TaskCell(fullAccess));
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
            taskList.setCellFactory(param -> new TaskCell(fullAccess));
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
                    taskList.setCellFactory(param -> new TaskCell(fullAccess));
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
            taskList.setCellFactory(param -> new TaskCell(fullAccess));
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
                    taskList.setCellFactory(param -> new TaskCell(fullAccess));
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
        taskDAO.searchTasksByTitle(searchText, User.getUserName(), currentCategoryName);
        List<TaskImpl> searchResults;
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

    /// /////////////////////////////////////// import export .///////////////////////////////
    public void handleExport(MouseEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Tasks");
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try {
                ArrayList<TaskImpl> tasks = TaskDAO.getInstance().getTasksByUserName(User.getUserName());

                // Register the custom serializer for TaskImpl
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(TaskImpl.class, new TaskImplSerializer())
                        .setPrettyPrinting()
                        .create();

                // Convert tasks to JSON
                String json = gson.toJson(tasks);

                try (FileWriter fileWriter = new FileWriter(file)) {
                    fileWriter.write(json);
                    System.out.println("Tasks exported successfully to: " + file.getAbsolutePath());
                }

                Notification successNotification = new Notification(
                         // ID will be auto-generated by the database
                        "Tasks are exported",
                        "Tasks exported successfully from the json file" ,
                        NotifType.TASK, // Assuming SYSTEM is the type for system notifications
                        LocalDate.now() // Current date
                );
                // Save the notification to the database
                notificationDAO.addNotification(successNotification, User.getUserName()); // Replace "current_user" with the actual username
                NotificationAlert.showAlert("Success", "Tasks are exported");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error exporting tasks: " + e.getMessage());
                NotificationAlert.showAlert("Failed", "Tasks are not exported");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    //public void handleImport(ActionEvent actionEvent){}
    public void handleImport(MouseEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Tasks");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                // Read the entire file content as a single string
                StringBuilder jsonContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonContent.append(line);
                }

                // Parse the JSON array into a list of maps
                Gson gson = new Gson();
                Type taskListType = new TypeToken<List<Map<String, String>>>() {}.getType();
                List<Map<String, String>> taskList = gson.fromJson(jsonContent.toString(), taskListType);

                // Iterate over the list of tasks
                for (Map<String, String> taskData : taskList) {
                    String title = taskData.get("title");
                    String description = taskData.get("description");
                    String statusString = taskData.get("status");
                    String priorityString = taskData.get("priority");
                    String reminderString = taskData.get("reminder");
                    String categoryName = taskData.get("categoryName");
                    String dueDateString = taskData.get("dueDate");
                    String creationDateString = taskData.get("creationDate");


                    // Validate all fields
                    if (title == null || title.trim().isEmpty()) {
                        NotificationAlert.showAlert("Failed", "tritle cannot be null or empty.");
                        throw new IllegalArgumentException("Title cannot be null or empty.");

                    }
                    if (description == null || description.trim().isEmpty()) {
                        NotificationAlert.showAlert("Failed", "Description cannot be null or empty.");
                        throw new IllegalArgumentException("Description cannot be null or empty.");
                    }
                    if (statusString == null || statusString.trim().isEmpty()) {
                        NotificationAlert.showAlert("Failed", "Status cannot be null or empty.");
                        throw new IllegalArgumentException("Status cannot be null or empty.");
                    }
                    if (priorityString == null || priorityString.trim().isEmpty()) {
                        NotificationAlert.showAlert("Failed", "Priority cannot be null or empty.");
                        throw new IllegalArgumentException("Priority cannot be null or empty.");
                    }
                    if (reminderString == null || reminderString.trim().isEmpty()) {
                        NotificationAlert.showAlert("Failed", "Reminder cannot be null or empty.");
                        throw new IllegalArgumentException("Reminder cannot be null or empty.");
                    }
                    if (categoryName == null || categoryName.trim().isEmpty()) {
                        NotificationAlert.showAlert("Failed", "Category name cannot be null or empty.");
                        throw new IllegalArgumentException("Category name cannot be null or empty.");
                    }
                    if (dueDateString == null || dueDateString.trim().isEmpty()) {
                        NotificationAlert.showAlert("Failed", "Due date cannot be null or empty.");
                        throw new IllegalArgumentException("Due date cannot be null or empty.");
                    }
                    if (creationDateString == null || creationDateString.trim().isEmpty()) {
                        NotificationAlert.showAlert("Failed", "Creation date cannot be null or empty.");
                        throw new IllegalArgumentException("Creation date cannot be null or empty.");
                    }

                    // Parse fields
                    int id = 1000; // Assign a default ID or generate one dynamically
                    Status status = Status.valueOf(statusString);
                    Priority priority = Priority.valueOf(priorityString);
                    Reminder reminder = Reminder.valueOf(reminderString);
                    LocalDate dueDate = parseDate(dueDateString);
                    LocalDate creationDate = parseDate(creationDateString);

                    // Create TaskImpl object
                    TaskImpl task = new TaskImpl(
                            id,
                            title,
                            description,
                            status,
                            dueDate,
                            creationDate,
                            priority,
                            new ArrayList<>(), // Initialize comments as an empty list
                            reminder,
                            categoryName,
                            User.getUserName()
                    );

                    // Insert the task into the database
                    TaskDAO.getInstance().addTask(task);
                }
                Notification successNotification = new Notification(
                         // ID will be auto-generated by the database
                        "Tasks are imported",
                        "Tasks imported successfully from the json file" ,
                        NotifType.TASK, // Assuming SYSTEM is the type for system notifications
                        LocalDate.now() // Current date
                );
                // Save the notification to the database
                notificationDAO.addNotification(successNotification, User.getUserName()); // Replace "current_user" with the actual username
                NotificationAlert.showAlert("Success", "Tasks are imported");
                System.out.println("Tasks imported successfully.");
            } catch (IOException | IllegalArgumentException e) {
                e.printStackTrace();
                System.out.println("Error importing tasks: " + e.getMessage());
                NotificationAlert.showAlert("Failed", "Tasks are not imported");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Helper method to parse JSON string into a Map
    private Map<String, String> parseJsonToMap(String json) {
        Map<String, String> map = new HashMap<>();
        try {
            // Remove curly braces and split by commas
            String[] keyValuePairs = json.replace("{", "").replace("}", "").split(",");
            for (String pair : keyValuePairs) {
                String[] entry = pair.split(":");
                if (entry.length == 2) {
                    // Trim quotes and whitespace
                    String key = entry[0].replaceAll("\"", "").trim();
                    String value = entry[1].replaceAll("\"", "").trim();
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error parsing JSON: " + e.getMessage());
        }
        return map;
    }

    // Helper method to parse date strings
    private LocalDate parseDate(String dateString) {
        String[] dateParts = dateString.split("-");
        int year = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[2]);
        return LocalDate.of(year, month, day);
    }
}

