package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import interfaces.ReminderManager;
import models.Reminder;

public class ReminderService implements ReminderManager{

    public void addReminder(Reminder reminder) {
        String query = "INSERT INTO Reminders (task_id, reminder_time, message) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, reminder.getTaskId());
            stmt.setTimestamp(2, new Timestamp(reminder.getReminderTime().getTime()));
            stmt.setString(3, reminder.getMessage());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Reminder> getRemindersForTask(int taskId) {
        List<Reminder> reminders = new ArrayList<>();
        String query = "SELECT * FROM Reminders WHERE task_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, taskId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Reminder reminder = new Reminder();
                reminder.setReminderId(rs.getInt("reminder_id"));
                reminder.setTaskId(rs.getInt("task_id"));
                reminder.setReminderTime(rs.getTimestamp("reminder_time"));
                reminder.setMessage(rs.getString("message"));
                reminders.add(reminder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reminders;
    }

    @Override
    public void setReminder(Reminder reminder) {
        String query = "INSERT INTO Reminders (task_id, reminder_time, message) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, reminder.getTaskId());
            stmt.setTimestamp(2, new Timestamp(reminder.getReminderTime().getTime()));
            stmt.setString(3, reminder.getMessage());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeReminder(int reminderId) {
        String query = "DELETE FROM Reminders WHERE reminder_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, reminderId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Reminder> getAllReminders() {
        List<Reminder> reminders = new ArrayList<>();
        String query = "SELECT * FROM Reminders";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Reminder reminder = new Reminder();
                reminder.setReminderId(rs.getInt("reminder_id"));
                reminder.setTaskId(rs.getInt("task_id"));
                reminder.setReminderTime(rs.getTimestamp("reminder_time"));
                reminder.setMessage(rs.getString("message"));
                reminders.add(reminder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reminders;
    }

}
