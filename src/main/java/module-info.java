module com.example.todolist {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires java.desktop;
    requires com.google.gson;
    requires java.sql;
    requires java.base; // This is implicitly required, but you can explicitly include it

    opens com.example.todolist to javafx.fxml;
    opens com.example.todolist.controller to javafx.fxml; // Allow FXML to access controllers

    opens com.example.todolist.model to com.google.gson; // Open your custom model package to Gson

    exports com.example.todolist;
    exports com.example.todolist.controller;
}