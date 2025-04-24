package com.codebroth.rewake.reminder.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.codebroth.rewake.reminder.domain.model.Reminder
import com.codebroth.rewake.reminder.notifications.ReminderNotificationService.Companion.EXTRA_REMINDER_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.DayOfWeek
import java.util.Calendar
import javax.inject.Inject

class ReminderAlarmScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val alarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun scheduleReminder(reminderId: Long, reminder: Reminder) {
        reminder.daysOfWeek.forEach { day ->
            val nextRun = Calendar.getInstance().apply {
                set(Calendar.DAY_OF_WEEK, day.toCalendarDay())
                set(Calendar.HOUR_OF_DAY, reminder.hour)
                set(Calendar.MINUTE, reminder.minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)

                if (timeInMillis <= System.currentTimeMillis()) {
                    add(Calendar.WEEK_OF_YEAR, 1)
                }
            }

            val pendingIntent = createPendingIntent(reminderId, day)

//            alarmManager.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                nextRun.timeInMillis,
//                AlarmManager.INTERVAL_DAY * 7,
//                pendingIntent
//            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    nextRun.timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, nextRun.timeInMillis, pendingIntent)
            }
        }
    }

    fun cancelReminder(reminderId: Long) {
        DayOfWeek.entries.forEach { day ->
            alarmManager.cancel(createPendingIntent(reminderId, day))
        }
    }

    fun rescheduleReminder(reminderId: Long, reminder: Reminder) {
        cancelReminder(reminderId)
        scheduleReminder(reminderId, reminder)
    }

    private fun createPendingIntent(reminderId: Long, day: DayOfWeek): PendingIntent {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra(EXTRA_REMINDER_ID, reminderId)
        }
        val requestCode = reminderId * 10 + day.ordinal
        return PendingIntent.getBroadcast(
            context,
            requestCode.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun DayOfWeek.toCalendarDay(): Int = when (this) {
        DayOfWeek.SUNDAY    -> Calendar.SUNDAY
        DayOfWeek.MONDAY    -> Calendar.MONDAY
        DayOfWeek.TUESDAY   -> Calendar.TUESDAY
        DayOfWeek.WEDNESDAY -> Calendar.WEDNESDAY
        DayOfWeek.THURSDAY  -> Calendar.THURSDAY
        DayOfWeek.FRIDAY    -> Calendar.FRIDAY
        DayOfWeek.SATURDAY  -> Calendar.SATURDAY
    }

}