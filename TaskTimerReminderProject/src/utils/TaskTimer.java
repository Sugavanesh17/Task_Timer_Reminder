package utils;

public class TaskTimer extends Thread {
    private int taskId;
    private long startTime;
    private long endTime;
    private boolean running;

    public TaskTimer(int taskId) {
        this.taskId = taskId;
        this.running = true;
        this.startTime = 0;
        this.endTime = 0;
    }
    
    public void run() {
        startTime = System.currentTimeMillis();
        while (running) {
            // Continuously update the elapsed time
            try {
                Thread.sleep(1000);  // Update every second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Capture the end time when the timer stops
        endTime = System.currentTimeMillis();
    }

    public void stopTimer() {
        running = false;
    }

    public long getElapsedTime() {
        // If the timer is still running, calculate time from start
        if (running) {
            return (System.currentTimeMillis() - startTime) / 1000;
        }
        // Otherwise, return the elapsed time based on stop time
        return (endTime - startTime) / 1000;
    }

    public long getElapsedSeconds() {
        return (endTime - startTime) / 1000;
    }

    public long getEndTime() {
        return endTime;
    }
}
