package com.example.todolist.view.fxml;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class HomeViewController {

    @FXML
    private TextField searchField;

    @FXML
    private Button importButton;

    @FXML
    private Button exportButton;

    @FXML
    private void initialize() {
        // Set up event handlers for the buttons
        importButton.setOnAction(event -> handleImport());
        exportButton.setOnAction(event -> handleExport());

        // Optionally, set up an event handler for the search field
        searchField.setOnAction(event -> handleSearch());
    }

    private void handleImport() {
        // Implement import logic here
        System.out.println("Importing data...");
    }

    private void handleExport() {
        // Implement export logic here
        System.out.println("Exporting data...");
    }

    private void handleSearch() {
        String query = searchField.getText();
        // Implement search logic here
        System.out.println("Searching for: " + query);
    }
}