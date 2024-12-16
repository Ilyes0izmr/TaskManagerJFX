package com.example.todolist.controller;

import com.example.todolist.model.Category;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

public class CollabCategoryCell extends ListCell<Category> {
    private HBox content;
    private Text name;
    private Text ownerName;
    private Pane spacer;
    private Button deleteButton;

    public CollabCategoryCell() {
        super();

        name = new Text();
        ownerName = new Text();
        spacer = new Pane();
        deleteButton = new Button("Delete");


        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(10, 1);

        HBox.setMargin(deleteButton, new Insets(0, 0, 0, 10));

        content = new HBox(name, ownerName, spacer, deleteButton);
        content.setSpacing(10);

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

            setGraphic(content);
        }
    }
}
