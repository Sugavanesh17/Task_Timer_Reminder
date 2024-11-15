package models;

import java.util.Date;

public class Task {
    private int taskId;
    private int userId;
    private String title;
    private String description;
    private String priority;
    private String status;
    private Date dueDate;
    private Date createdAt;
    
    public Task(int taskId, int userId, String title, String description, String priority, String status, Date dueDate, Date createdAt) {
        this.taskId = taskId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
    }
    public Task(int taskId, String title, String description, String priority, Date dueDate) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
    }

	public Task() {
	}
	public int getTaskId() {
		return taskId;
	}
	public int getUserId() {
		return userId;
	}
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public String getPriority() {
		return priority;
	}
	public String getStatus() {
		return status;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
}