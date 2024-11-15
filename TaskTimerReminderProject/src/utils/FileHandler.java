package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import models.Reminder;
import models.Task;

public class FileHandler {

    private static final String TASK_FILE = "C:\\Users\\Sugavaneshwaran\\Downloads\\tasks.txt";  // File path for saving tasks
    private static final String REMINDER_FILE = "C:\\Users\\Sugavaneshwaran\\Downloads\\reminders.txt";  // File path for saving reminders

    public void saveTasks(List<Task> tasks, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TASK_FILE, append))) {
            for (Task task : tasks) {
                writer.write("Task ID: " + task.getTaskId());
                writer.newLine();
                writer.write("Title: " + task.getTitle());
                writer.newLine();
                writer.write("Description: " + task.getDescription());
                writer.newLine();
                writer.write("Priority: " + task.getPriority());
                writer.newLine();
                writer.write("Status: " + task.getStatus());
                writer.newLine();
                writer.write("Due Date: " + task.getDueDate());
                writer.newLine();
                writer.write("Created At: 2024-11-15" );
                writer.newLine();
                writer.write("------------------------------");
                writer.newLine();
            }
            System.out.println("Tasks successfully saved to file: " + TASK_FILE);
        } catch (IOException e) {
            System.err.println("Error saving tasks to file: " + e.getMessage());
        }
    }

    public void saveReminders(List<Reminder> reminders, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(REMINDER_FILE, append))) {
            for (Reminder reminder : reminders) {
                writer.write("Reminder ID: " + reminder.getReminderId());
                writer.newLine();
                writer.write("Task ID: " + reminder.getTaskId());
                writer.newLine();
                writer.write("Reminder Time: " + reminder.getReminderTime());
                writer.newLine();
                writer.write("Message: " + reminder.getMessage());
                writer.newLine();
                writer.write("Created At: 2024-11-15" );
                writer.newLine();
                writer.write("------------------------------");
                writer.newLine();
            }
            System.out.println("Reminders successfully saved to file: " + REMINDER_FILE);
        } catch (IOException e) {
            System.err.println("Error saving reminders to file: " + e.getMessage());
        }
    }
}
