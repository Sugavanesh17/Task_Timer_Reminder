package interfaces;

import models.Task;

import java.util.List;

public interface TaskManager {
    void addTask(Task task);
    void removeTask(int taskId);
    void updateTask(Task task);
    List<Task> getAllTasks();
}
