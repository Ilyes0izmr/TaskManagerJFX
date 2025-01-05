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
    Pane spacer = new Pane();
    Button deleteButton;

    public CollabCategoryCell() {
        super();

        // Apply CSS classes
        name.getStyleClass().add("collab-name");
        ownerName.getStyleClass().add("collab-owner");
        spacer.getStyleClass().add("collab-spacer");

        deleteButton = new Button("Delete");
        deleteButton.getStyleClass().add("collab-delete-button");

        // Set up the HBox
        HBox.setHgrow(spacer, Priority.ALWAYS); // Make spacer expand
        HBox.setMargin(deleteButton, new Insets(0, 8, 0, 0)); // Right margin for delete button
        HBox.setMargin(ownerName, new Insets(0, 12, 0, 12)); // Margins around owner name

        // Add elements to the HBox
        hbox.getChildren().addAll(name, ownerName, spacer, deleteButton);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(6, 10, 6, 10)); // Padding around the entire cell
        hbox.getStyleClass().add("collab-hbox");

        // Apply ListCell styling
        this.getStyleClass().add("list-cell");

        HomeController homeController = new HomeController();
        deleteButton.setOnAction(event -> {
            Category category = getItem();
            if (category != null) {
                homeController.deleteCollabCategory(category);
            }
        });
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
            setGraphic(hbox);
        }
    }
}