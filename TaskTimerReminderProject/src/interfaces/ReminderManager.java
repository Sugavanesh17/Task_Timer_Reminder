package interfaces;

import models.Reminder;

import java.util.List;

public interface ReminderManager {
    void setReminder(Reminder reminder);
    void removeReminder(int reminderId);
    List<Reminder> getAllReminders();
}