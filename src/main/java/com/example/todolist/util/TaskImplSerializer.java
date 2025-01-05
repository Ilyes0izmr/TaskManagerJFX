package com.example.todolist.util;
import com.example.todolist.model.TaskImpl;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 * @brief Custom JSON serializer for the TaskImpl class.
 * This class implements the JsonSerializer interface to provide custom serialization
 * for TaskImpl objects into JSON format. It converts TaskImpl fields into a JsonObject
 * with specific properties such as id, title, description, status, priority, reminder,
 * categoryName, dueDate, and creationDate.
 * @note this class is build for the sake of the date object since Gson cant access the private properties of the class LocalDate
 * @author izemmouren ilyes
 */
public class TaskImplSerializer implements JsonSerializer<TaskImpl> {

    /**
     *
     * @param task The Task object to serialize.
     * @param typeOfSrc The type of the source object (TaskImpl).
     * @param context The serialization context provided by Gson.
     * @return A JsonElement representing the serialized TaskImpl object.
     *
     * @note If priority or reminder fields are null, they are explicitly set to null
     *       in the JSON output to avoid missing fields.
     * //TODO Consider adding support for additional fields or custom formatting options.
     * @see TaskImpl For the structure of the TaskImpl class being serialized.
     * @see JsonSerializer For the interface being implemented.
     */
    @Override
    public JsonElement serialize(TaskImpl task, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        // Add fields to the JSON object
        jsonObject.addProperty("id", task.getId());
        jsonObject.addProperty("title", task.getTitle());
        jsonObject.addProperty("description", task.getDescription());
        jsonObject.addProperty("status", task.getStatus().name());
        jsonObject.addProperty("priority", task.getPriority() == null ? null : task.getPriority().name());
        jsonObject.addProperty("reminder", task.getReminder() == null ? null : task.getReminder().name());
        jsonObject.addProperty("categoryName", task.getCategoryName());
        // Use the custom functions to get LocalDate as String
        jsonObject.addProperty("dueDate", task.getDueDateAsString());
        jsonObject.addProperty("creationDate", task.getCreationDateAsString());

        return jsonObject;
    }
}