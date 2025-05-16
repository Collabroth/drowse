package com.codebroth.rewake.reminder.data.notification

import com.codebroth.rewake.core.data.scheduling.Scheduler
import com.codebroth.rewake.core.data.scheduling.Scheduler.Companion.EXTRA_ONE_SHOT
import com.codebroth.rewake.core.data.scheduling.TriggerTimeCalculator
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
        repo.getAllReminders()
            .first()
            .forEach { reminder ->
                scheduleNext(reminder, reminder.id)
            }
    }

//    fun schedule(reminder: Reminder, reminderId: Int) {
//        scheduler.schedule(
//            id = reminderId,
//            daysOfWeek = reminder.daysOfWeek,
//            hour = reminder.hour,
//            minute = reminder.minute,
//            receiver = ReminderReceiver::class.java
//        ) {
//            putExtra(EXTRA_REMINDER_ID, reminderId)
//        }
//    }

    fun scheduleNext(reminder: Reminder, reminderId: Int) {
        val zonedDateTime = TriggerTimeCalculator.nextTrigger(
            daysOfWeek = reminder.daysOfWeek,
            hour = reminder.hour,
            minute = reminder.minute
        )
        scheduler.scheduleAt(
            id = reminderId,
            triggerAtMillis = zonedDateTime.toInstant().toEpochMilli(),
            receiver = ReminderReceiver::class.java
        ) {
            putExtra(EXTRA_REMINDER_ID, reminderId)
            putExtra(EXTRA_ONE_SHOT, reminder.daysOfWeek.isEmpty())
        }
    }

    /**
     * Eventually write an SQL query to get reminder by Id.
     */
    suspend fun scheduleNext(reminderId: Int) {
        /**
         * Temporary Solution
         */
        val reminder = repo
            .getAllReminders()
            .first()
            .first { it.id == reminderId }

        scheduleNext(reminder, reminderId)
    }

    fun cancel(reminderId: Int) {
        scheduler.cancel(reminderId, ReminderReceiver::class.java)
    }

    fun reschedule(reminder: Reminder, reminderId: Int) {
        cancel(reminderId)
        scheduleNext(reminder, reminderId)
    }
}