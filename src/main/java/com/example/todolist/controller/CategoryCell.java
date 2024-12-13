package com.example.todolist.controller;

import com.example.todolist.model.Category;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CategoryCell extends ListCell<Category> {
    HBox hbox = new HBox();
    Label categoryLabel = new Label();
    Pane pane = new Pane();
    MenuButton menuButton = new MenuButton("Options");

    public CategoryCell() {
        super();
        hbox.getChildren().addAll(categoryLabel, pane, menuButton);
        HBox.setHgrow(pane, Priority.ALWAYS);

        MenuItem editItem = new MenuItem("Edit");
        MenuItem deleteItem = new MenuItem("Delete");
        menuButton.getItems().addAll(editItem, deleteItem);

        editItem.setOnAction(event -> {
            Category category = getItem();
            if (category != null) {
                handleEditCategory(category);
            }
        });

        deleteItem.setOnAction(event -> {
            Category category = getItem();
            if (category != null) {
                handleDeleteCategory(category);
            }
        });
    }

    @Override
    protected void updateItem(Category category, boolean empty) {
        super.updateItem(category, empty);
        if (empty || category == null) {
            setGraphic(null);
        } else {
            categoryLabel.setText(category.getName());
            setGraphic(hbox);
        }
    }

    private void handleEditCategory(Category category) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/todolist/view/fxml/EditCategoryView.fxml"));
            Pane pane = loader.load();

            EditCategoryController controller = loader.getController();
            controller.setCategory(category);

            Stage stage = new Stage();
            stage.setTitle("Edit Category");
            stage.setScene(new Scene(pane));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            HomeController.getInstance().refreshCategories();
            HomeController.getInstance().refresh();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading EditCategoryView.");
        }
    }

    private void handleDeleteCategory(Category category) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/todolist/view/fxml/DeleteCategoryView.fxml"));
            Pane pane = loader.load();

            DeleteCategoryController controller = loader.getController();
            controller.setCategory(category);

            Stage stage = new Stage();
            stage.setTitle("Delete Category");
            stage.setScene(new Scene(pane));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            HomeController.getInstance().refreshCategories();
            HomeController.getInstance().refresh();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading DeleteCategoryView.");
        }
    }
}

