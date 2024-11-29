module com.example.todolist {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.todolist to javafx.fxml;
    opens com.example.todolist.controller to javafx.fxml; // Allow FXML to access controllers

    exports com.example.todolist;
    exports com.example.todolist.controller;
}
