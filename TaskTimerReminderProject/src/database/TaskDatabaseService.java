package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Task;


public class TaskDatabaseService {

    public void saveTask(Task task) throws SQLException {
        String query = "INSERT INTO tasks (title, description, priority, status, due_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setString(3, task.getPriority());
            statement.setString(4, task.getStatus());
            statement.setTimestamp(5, new Timestamp(task.getDueDate().getTime()));
            statement.executeUpdate();
        }
    }

    public List<Task> getAllTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM tasks";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Task task = new Task(
                        resultSet.getInt("task_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("priority"),
                        resultSet.getDate("due_date")
                );
                tasks.add(task);
            }
        }
        return tasks;
    }
}
