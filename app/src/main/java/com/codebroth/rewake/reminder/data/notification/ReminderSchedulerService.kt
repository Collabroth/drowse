package com.codebroth.rewake.reminder.data.notification

import com.codebroth.rewake.core.data.Scheduler
import com.codebroth.rewake.reminder.data.ReminderRepository
import com.codebroth.rewake.reminder.data.notification.ReminderReceiver.Companion.EXTRA_REMINDER_ID
import com.codebroth.rewake.reminder.domain.model.Reminder
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReminderSchedulerService @Inject constructor(
    private val scheduler: Scheduler,
    private val repo: ReminderRepository
) {
    suspend fun scheduleAll() {
        repo.getAllReminders().first().forEach { reminder ->
            schedule(reminder, reminder.id)
        }
    }

    fun schedule(reminder: Reminder, reminderId: Int) {
        scheduler.schedule(
            id = reminderId,
            daysOfWeek = reminder.daysOfWeek,
            hour = reminder.hour,
            minute = reminder.minute,
            receiver = ReminderReceiver::class.java
        ) {
            putExtra(EXTRA_REMINDER_ID, reminderId)
        }
    }

    fun cancel(reminderId: Int) {
        scheduler.cancel(reminderId, ReminderReceiver::class.java)
    }

    fun reschedule(reminder: Reminder, reminderId: Int) {
        cancel(reminderId)
        schedule(reminder, reminderId)
    }
}