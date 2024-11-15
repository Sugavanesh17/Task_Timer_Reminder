package app;

import models.*;
import services.*;
import database.DatabaseConnection;
import utils.FileHandler;

import java.sql.*;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalDateTime;

public class App {
    private static TaskService taskService = new TaskService();
    private static ReminderService reminderService = new ReminderService();
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            System.out.println("-----  Welcome to Task Timer and Reminder System  -----");
            System.out.println("1. Login");
            System.out.println("2. Create User");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    createUser();
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    private static void login() {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();  
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        if (authenticateUser(userId, password)) {
            System.out.println("Login successful!");
            manageTasks(userId);
            List<Task> tasks = taskService.getAllTasks(userId);  
            List<Reminder> reminders = reminderService.getAllReminders();  
            FileHandler fileHandler = new FileHandler();  
            fileHandler.saveTasks(tasks,true);
            fileHandler.saveReminders(reminders,true);
        } else {
            System.out.println("Invalid login credentials. Please try again.");
        }
    }
    private static boolean authenticateUser(int userId, String password) {
        String query = "SELECT * FROM Users WHERE user_id = ? AND password = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, password);
            ResultSet resultSet = stmt.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private static void createUser() {
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        if (addUserToDatabase(firstName, lastName, email, password)) {
            System.out.println("User created successfully.\nYou can now log in");
        } else {
            System.out.println("Failed to create user. Please try again.");
        }
    }

    private static boolean addUserToDatabase(String firstName, String lastName, String email, String password) {
        String query = "INSERT INTO Users (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, password);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void manageTasks(int userId) {
        boolean exit = false;

        while (!exit) {
            System.out.println("------  Task Manager Menu  ------");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Set Timer for a Task");
            System.out.println("4. End Timers for Tasks");
            System.out.println("5. Add Reminder to Task");
            System.out.println("6. View Reminders for Task");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  

            switch (choice) {
                case 1:
                    addTask(userId);
                    saveTasksToFile(userId);
                    break;
                case 2:
                    viewTasks(userId);
                    break;
                case 3:
                    setTimerForTask(userId);  
                    break;
                case 4:
                    viewTimers(userId);
                    break;
                case 5:
                    addReminder(userId); 
                    saveRemindersToFile();
                    break;
                case 6:
                    viewReminders(userId);  
                    break;
                case 7:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addReminder(int userId) {
        ReminderService reminderService = new ReminderService();
        Reminder reminder = new Reminder();
        
        System.out.print("Enter Task ID to set reminder for: ");
        int taskId = scanner.nextInt();
        scanner.nextLine();  
        
        System.out.print("Enter Reminder Message: ");
        String message = scanner.nextLine();
        
        System.out.print("Enter Reminder Time (yyyy-MM-dd HH:mm:ss): ");
        String time = scanner.nextLine();

        try {
            java.util.Date reminderTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
            reminder.setTaskId(taskId);
            reminder.setReminderTime(reminderTime);
            reminder.setMessage(message);
            reminderService.addReminder(reminder);
            System.out.println("Reminder set successfully.");
        } catch (java.text.ParseException e) {
            System.out.println("Invalid date format. Please try again.");
        }
    }

    private static void viewReminders(int userId) {
        ReminderService reminderService = new ReminderService();
        var reminders = reminderService.getRemindersForTask(userId);

        System.out.println("Reminders:");
        for (var reminder : reminders) {
            System.out.println("Task ID: " + reminder.getTaskId());
            System.out.println("Reminder Time: " + reminder.getReminderTime());
            System.out.println("Message: " + reminder.getMessage());
            System.out.println("---------------");
            Task task = new Task();
            task.setTaskId(reminder.getTaskId());
            task.setTitle(reminder.getMessage()); 
            task.setDueDate(reminder.getReminderTime()); 
            scheduleReminder(task,15 * 60 * 1000);
        }
    }
    public static void viewTimers(int userId) {
        String query = "SELECT * FROM Timers t INNER JOIN Tasks tk ON t.task_id = tk.task_id WHERE tk.user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            boolean foundTimers = false;  
            System.out.println("Timers for User ID " + userId + ":");
            while (rs.next()) {
                foundTimers = true;
                int timerId = rs.getInt("timer_id");
                int taskId = rs.getInt("task_id");
                String status = rs.getString("status");
                Timestamp startTime = rs.getTimestamp("start_time");
                Timestamp endTime = rs.getTimestamp("end_time");
                int elapsedTime = rs.getInt("elapsed_time");
                System.out.println("Timer ID: " + timerId);
                System.out.println("Task Title: " + rs.getString("title"));
                System.out.println("Start Time: " + startTime);
                if (endTime != null) {
                    System.out.println("End Time: " + endTime);
                    System.out.println("Elapsed Time: " + elapsedTime + " seconds");
                } else {
                    long currentElapsedTime = (System.currentTimeMillis() - startTime.getTime()) / 1000; 
                    System.out.println("End Time: Timer still running");
                    System.out.println("Elapsed Time: " + currentElapsedTime + " seconds");
                }

                System.out.println("-----------------------------");
            }
            if (!foundTimers) {
                System.out.println("No timers found for the given user.");
            } else {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter the Timer ID to stop or 0 to exit: ");
                int timerIdToStop = scanner.nextInt();
                if (timerIdToStop != 0) {
                    stopTimer(conn, timerIdToStop);
                }
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while retrieving timers: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void stopTimer(Connection conn, int timerIdToStop) {
        String query = "SELECT * FROM Timers WHERE timer_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, timerIdToStop);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int timerId = rs.getInt("timer_id");
                Timestamp startTime = rs.getTimestamp("start_time");
                if (startTime != null) {
                    long currentElapsedTime = (System.currentTimeMillis() - startTime.getTime()) / 1000; 
                    String updateQuery = "UPDATE Timers SET end_time = ?, elapsed_time = ?, status = ? WHERE timer_id = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                        updateStmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                        updateStmt.setLong(2, currentElapsedTime);
                        updateStmt.setString(3, "Stopped");
                        updateStmt.setInt(4, timerId);
                        updateStmt.executeUpdate();
                        System.out.println("Timer ID " + timerId + " stopped. Elapsed Time: " + currentElapsedTime + " seconds");
                    }
                }
            } else {
                System.out.println("No timer found with ID " + timerIdToStop);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while stopping the timer: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private static void addTask(int userId) {
        System.out.println("Enter Task Details: ");
        System.out.print("Task ID (Enter 0 for auto-generation): ");
        int taskId = Integer.parseInt(scanner.nextLine()); 
        System.out.print("Title: ");
        String title = scanner.nextLine();       
        System.out.print("Description: ");
        String description = scanner.nextLine();      
        System.out.print("Priority (LOW,MEDIUM,HIGH):");
        String priority = scanner.nextLine();
        System.out.print("Status (Pending, Completed):");
        String status = scanner.nextLine();       
        System.out.print("Due Date (YYYY-MM-DD): ");
        String dueDateStr = scanner.nextLine();       
        Date dueDate = Date.valueOf(dueDateStr); 
        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp createdAt = Timestamp.valueOf(currentDateTime);
        Task task = new Task(taskId, userId, title, description, priority, status, dueDate, createdAt);
        taskService.addTask(task);
        System.out.println("Task added successfully.");
    }
    
    private static void setTimerForTask(int userId) {
        List<Task> tasks = taskService.getAllTasks(userId);
        if (tasks == null || tasks.isEmpty()) {
            System.out.println("No tasks available to set timer.");
            return;
        }
        
        System.out.println("Select a task to set timer:");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            System.out.println((i + 1) + ". " + task.getTitle() + " (Due: " + task.getDueDate() + ")");
        }        
        System.out.print("Enter task number to set timer: ");
        int taskChoice = scanner.nextInt();
        scanner.nextLine();  
        
        if (taskChoice < 1 || taskChoice > tasks.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }        
        Task selectedTask = tasks.get(taskChoice - 1);        
        startTimer(selectedTask);
        System.out.println("Timer started for task: " + selectedTask.getTitle());
    }

    private static void startTimer(Task task) {
    	TimerService timerService = new TimerService();
        timerService.startTimerForTask(task);
        new Thread(() -> {
            long timeRemaining = task.getDueDate().getTime() - System.currentTimeMillis();
            if (timeRemaining > 0) {
                try {
                    Thread.sleep(timeRemaining);
                    System.out.println("Time's up! Task \"" + task.getTitle() + "\" is due now.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void scheduleReminder(Task task, long reminderOffset) {
        Timer timer = new Timer();
        long reminderTime = task.getDueDate().getTime() - System.currentTimeMillis() - reminderOffset;
        if (reminderTime > 0) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Reminder: Task \"" + task.getTitle() + "\" is due soon.");
                }
            }, reminderTime);
        } else {
            System.out.println("Due date for task \"" + task.getTitle() + "\" is too close or has already passed.");
        }
    }
    private static void viewTasks(int userId) {
        List<Task> tasks = taskService.getAllTasks(userId);
        if (tasks != null && !tasks.isEmpty()) {
            System.out.println("Tasks List:");
            for (Task task : tasks) {
            	System.out.println("===============================================");
                System.out.println("Task ID: " + task.getTaskId());
                System.out.println("Title: " + task.getTitle());
                System.out.println("Description: " + task.getDescription());
                System.out.println("Priority: " + task.getPriority());
                System.out.println("Status: " + task.getStatus());
                System.out.println("Due Date: " + task.getDueDate());          
                System.out.println("===============================================");
            }
        } else {
            System.out.println("No tasks found.");
        }
    }
    private static void saveTasksToFile(int userId) {
        List<Task> tasks = taskService.getAllTasks(userId);
        FileHandler fileHandler=new FileHandler();
		fileHandler.saveTasks(tasks,true);  
    }

    private static void saveRemindersToFile() {
        List<Reminder> reminders = reminderService.getAllReminders();
        FileHandler fileHandler=new FileHandler();
        fileHandler.saveReminders(reminders,true);  
    }

}