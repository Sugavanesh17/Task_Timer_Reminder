package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Timer {
    private int timerId;
    private int taskId;
    private Date startTime;
    private Date endTime;
    private int elapsedTime;  
    private String status;
    public Timer(ResultSet rs) throws SQLException {
        this.timerId = rs.getInt("timer_id");
        this.startTime = rs.getTimestamp("start_time");
        this.elapsedTime = (int) rs.getLong("elapsed_time");
        this.status = rs.getString("status");
    }
	public int getTimerId() {
		return timerId;
	}
	public int getTaskId() {
		return taskId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public int getElapsedTime() {
		return elapsedTime;
	}
	public String getStatus() {
		return status;
	}
	public void setTimerId(int timerId) {
		this.timerId = timerId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setElapsedTime(int elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}