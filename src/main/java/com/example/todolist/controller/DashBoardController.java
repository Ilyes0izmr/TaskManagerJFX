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

        // Create PieChart.Data objects for each slice
        PieChart.Data completedData = new PieChart.Data("Completed", completedTasks);
        PieChart.Data pendingData = new PieChart.Data("Pending", pendingTasks);

        // Add data to the pie chart first
        progressPieChart.getData().addAll(completedData, pendingData);

        // Set custom colors for each slice
        completedData.getNode().setStyle("-fx-pie-color: #15F5BA;"); // Green color
        pendingData.getNode().setStyle("-fx-pie-color: #EB3678;"); // Pink color

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
            colorRect.setFill(data.getName().equals("Completed") ? Color.valueOf("#15F5BA") : Color.valueOf("#EB3678"));

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

        // Customize the Y-axis
        yAxis.setAutoRanging(false); // Disable auto-ranging
        yAxis.setLowerBound(0); // Set the lower bound to 0
        yAxis.setUpperBound(20); // Set the upper bound to 5
        yAxis.setTickUnit(1); // Set the tick unit to 1 (increment by 1)

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
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        // Customize the Y-axis
        NumberAxis priorityYAxis = (NumberAxis) priorityBarChart.getYAxis();
        priorityYAxis.setAutoRanging(false); // Disable auto-ranging
        priorityYAxis.setLowerBound(0); // Set the lower bound to 0
        priorityYAxis.setUpperBound(20); // Set the upper bound to 5
        priorityYAxis.setTickUnit(1); // Set the tick unit to 1 (increment by 1)


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