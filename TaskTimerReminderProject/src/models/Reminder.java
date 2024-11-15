package models;

import java.util.Date;

public class Reminder {
    private int reminderId;
    private int taskId;
    private Date reminderTime;
    private String message;
    private Date createdAt;
	public int getReminderId() {
		return reminderId;
	}
	public int getTaskId() {
		return taskId;
	}
	public Date getReminderTime() {
		return reminderTime;
	}
	public String getMessage() {
		return message;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setReminderId(int reminderId) {
		this.reminderId = reminderId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public void setReminderTime(Date reminderTime) {
		this.reminderTime = reminderTime;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
