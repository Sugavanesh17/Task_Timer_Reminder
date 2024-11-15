package services;

import java.sql.*;
import java.time.LocalDateTime;
import database.DatabaseConnection;
import models.Task;

public class TimerService {
	
    public void startTimerForTask(Task task) {
        String insertQuery = "INSERT INTO Timers (task_id, start_time, status) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

            stmt.setInt(1, task.getTaskId());
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(3, "Active");
            stmt.executeUpdate();

            System.out.println("Timer entry created for task ID: " + task.getTaskId());

        } catch (SQLException e) {
            System.out.println("Error starting timer: " + e.getMessage());
        }
    }

}
