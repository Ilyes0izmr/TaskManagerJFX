package com.example.todolist.controller;

import com.example.todolist.model.Category;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class CollabCategoryCell extends ListCell<Category> {
    HBox hbox = new HBox();
    Label name = new Label();
    Label ownerName = new Label();
    Button deleteButton;

    public CollabCategoryCell() {
        super();

        // Apply CSS classes
        name.getStyleClass().add("collab-name");
        ownerName.getStyleClass().add("collab-owner");

        deleteButton = new Button("Delete");
        deleteButton.getStyleClass().add("collab-delete-button");

        // Set up the HBox

        HBox.setMargin(deleteButton, new Insets(0, 0, 0, 5)); // Smaller margin

        // Add elements to the HBox
        hbox.getChildren().addAll(name, ownerName, deleteButton);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setSpacing(5); // Reduced spacing between elements
        hbox.getStyleClass().add("collab-hbox");

        // Apply ListCell styling
        this.getStyleClass().add("list-cell");
    }

    @Override
    protected void updateItem(Category category, boolean empty) {
        super.updateItem(category, empty);
        if (empty || category == null) {
            setText(null);
            setGraphic(null);
        } else {
            name.setText(category.getName());
            ownerName.setText("Owner: " + category.getUserName());

            // Delete button functionality
            deleteButton.setOnAction(event -> {
                System.out.println("Deleting collab category: " + category.getName());
                HomeController.getInstance().deleteCollabCategory(category);
            });

            setGraphic(hbox);
        }
    }
}