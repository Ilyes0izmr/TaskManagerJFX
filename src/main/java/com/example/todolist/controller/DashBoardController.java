package com.example.todolist.controller;

import com.example.todolist.dao.TaskDAO;
import com.example.todolist.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.Map;

public class DashBoardController {

    @FXML
    private PieChart progressPieChart;

    @FXML
    private VBox keyContainer; // Container for the dynamic key

    @FXML
    private BarChart<String, Number> taskBarChart; // Bar chart for task categories

    @FXML
    private CategoryAxis xAxis; // X-axis for the bar chart

    @FXML
    private NumberAxis yAxis; // Y-axis for the bar chart

    @FXML
    private BarChart<String, Number> priorityBarChart;

    @FXML
    private ListView<String> upcomingDeadlinesListView;

    // DAO for fetching task data
    private TaskDAO taskDAO;

    // Method to initialize the dashboard (called automatically after FXML is loaded)
    public void initialize() {
        // Initialize the DAO
        taskDAO = new TaskDAO();

        // Fetch data from the DAO
        int totalTasks = taskDAO.getNumberOfTasks(User.getUserName());
        int completedTasks = taskDAO.getNumberOfCompleted(User.getUserName());
        int pendingTasks = totalTasks - completedTasks;

        // Populate the PieChart
        populateProgressPieChart(completedTasks, pendingTasks);

        // Generate the key dynamically
        generateKey();

        // Populate the Bar Chart for task categories
        populateTaskBarChart();

        // Populate the Bar Chart for task priorities
        populatePriorityBarChart();

        populateUpcomingDeadlinesListView();
    }

    // Method to populate the PieChart
    private void populateProgressPieChart(int completedTasks, int pendingTasks) {
        // Clear existing data
        progressPieChart.getData().clear();

        // Add data to the pie chart
        progressPieChart.getData().add(new PieChart.Data("Completed", completedTasks));
        progressPieChart.getData().add(new PieChart.Data("Pending", pendingTasks));

        // Customize the pie chart
        progressPieChart.setLabelsVisible(false); // Hide labels inside the pie chart
    }

    // Method to generate the key dynamically
    private void generateKey() {
        // Clear existing key items
        keyContainer.getChildren().clear();

        // Loop through the pie chart data and create key items
        for (PieChart.Data data : progressPieChart.getData()) {
            // Create a colored rectangle
            Rectangle colorRect = new Rectangle(20, 20);
            colorRect.setFill(data.getName().equals("Completed") ? Color.GREEN : Color.RED);

            // Create a label for the description
            Label descriptionLabel = new Label(data.getName() + " (" + (int) data.getPieValue() + ")");

            // Add the rectangle and label to an HBox
            HBox keyItem = new HBox(5, colorRect, descriptionLabel);
            keyItem.setAlignment(Pos.CENTER_LEFT);

            // Add the key item to the key container
            keyContainer.getChildren().add(keyItem);
        }
    }

    // Method to populate the Bar Chart for task categories
    private void populateTaskBarChart() {
        // Clear existing data
        taskBarChart.getData().clear();

        // Fetch task counts by category from the DAO
        Map<String, Integer> taskCountsByCategory = taskDAO.getTaskCountByCategory(User.getUserName());

        // Debugging: Print the fetched data
        System.out.println("Task Counts by Category:");
        for (Map.Entry<String, Integer> entry : taskCountsByCategory.entrySet()) {
            System.out.println("Category: " + entry.getKey() + ", Task Count: " + entry.getValue());
        }

        // Create a series for the bar chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Task Count");

        // Add data to the series
        for (Map.Entry<String, Integer> entry : taskCountsByCategory.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        // Add the series to the bar chart
        taskBarChart.getData().add(series);
    }

    // Method to populate the Bar Chart for task priorities
    private void populatePriorityBarChart() {
        // Clear existing data
        priorityBarChart.getData().clear();

        // Fetch task counts by priority from the DAO
        Map<String, Integer> taskCountsByPriority = taskDAO.getTaskCountByPriority(User.getUserName());

        // Debugging: Print the fetched data
        System.out.println("Task Counts by Priority:");
        for (Map.Entry<String, Integer> entry : taskCountsByPriority.entrySet()) {
            System.out.println("Priority: " + entry.getKey() + ", Task Count: " + entry.getValue());
        }

        // Create a series for the bar chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Task Count");


        // Add data to the series
        for (Map.Entry<String, Integer> entry : taskCountsByPriority.entrySet()) {
            // Ensure the types match: xAxis is CategoryAxis (String), yAxis is NumberAxis (Number)
            String priority = entry.getKey(); // Priority is a String (e.g., "High", "Medium", "Low")
            Number taskCount = entry.getValue(); // Task count is a Number (e.g., 5, 3, 2)

            // Debugging: Print the data being added
            System.out.println("Adding to chart: Priority = " + priority + ", Task Count = " + taskCount);

            // Add the data to the series
            series.getData().add(new XYChart.Data<>(priority, taskCount));
        }

        // Add the series to the bar chart
        priorityBarChart.getData().add(series);
    }

    private void populateUpcomingDeadlinesListView() {
        // Fetch upcoming deadlines (task titles) from the DAO
        List<String> upcomingDeadlines = taskDAO.getUpcomingDeadlines(User.getUserName());

        // Debugging: Print the fetched task titles
        System.out.println("Upcoming Deadlines:");
        for (String title : upcomingDeadlines) {
            System.out.println(title); // Print only the title
        }

        // Convert the list of titles to an ObservableList
        ObservableList<String> taskTitles = FXCollections.observableArrayList(upcomingDeadlines);

        // Set the items of the ListView to the task titles
        upcomingDeadlinesListView.setItems(taskTitles);

        System.out.println("\ndone");

    }
}