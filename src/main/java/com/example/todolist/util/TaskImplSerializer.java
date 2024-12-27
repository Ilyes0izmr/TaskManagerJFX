package com.example.todolist.util;

import com.example.todolist.model.TaskImpl;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class TaskImplSerializer implements JsonSerializer<TaskImpl> {

    @Override
    public JsonElement serialize(TaskImpl task, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        // Add fields to the JSON object
        jsonObject.addProperty("id", task.getId());
        jsonObject.addProperty("title", task.getTitle());
        jsonObject.addProperty("description", task.getDescription());
        jsonObject.addProperty("status", task.getStatus().name());
        jsonObject.addProperty("priority", task.getPriority() == null? null : task.getPriority().name());
        jsonObject.addProperty("reminder", task.getReminder() == null? null : task.getReminder().name());
        jsonObject.addProperty("categoryName", task.getCategoryName());
        // Use the custom functions to get LocalDate as String
        jsonObject.addProperty("dueDate", task.getDueDateAsString());
        jsonObject.addProperty("creationDate", task.getCreationDateAsString());

        return jsonObject;
    }
}