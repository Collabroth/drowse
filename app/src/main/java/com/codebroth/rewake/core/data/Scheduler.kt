package com.codebroth.rewake.core.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.DayOfWeek
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Scheduler @Inject constructor(
    @ApplicationContext private val context: Context,
    private val alarmManager: AlarmManager
) {

    /**
     * Schedule one exact alarm.
     * @param id             unique identifier (used in requestCode)
     * @param daysOfWeek     which days to repeat on (empty = one-shot)
     * @param hour           time of day
     * @param minute         time of day
     * @param receiver       the BroadcastReceiver class to fire
     * @param prepareIntent  lambda to put extras into that Intent before wrapping in a PendingIntent
     */
    fun schedule(
        id: Int,
        daysOfWeek: Set<DayOfWeek>,
        hour: Int,
        minute: Int,
        receiver: Class<*>,
        prepareIntent: Intent.() -> Unit = {}
    ) {
        val now = System.currentTimeMillis()
        val cal = Calendar.getInstance()

        daysOfWeek.forEach  { day ->
            val nextRun = getNextOccurrenceCalendar(cal, day, hour, minute, now)

            val intent = Intent(context, receiver).apply {
                prepareIntent()
            }
            val requestCode = id * 10 + (day.ordinal)
            val pendingIntent = createPendingIntent(requestCode, intent)

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                nextRun.timeInMillis,
                pendingIntent
            )
        }
    }

    fun cancel(id: Int, receiver: Class<*>) {
        DayOfWeek.entries.forEach { day ->
            val requestCode = id * 10 + (day.ordinal)
            val intent = Intent(context, receiver)
            val pendingIntent = createPendingIntent(requestCode, intent)

            alarmManager.cancel(pendingIntent)
        }
    }

    private fun createPendingIntent(
        requestCode: Int,
        intent: Intent
    ): PendingIntent = PendingIntent.getBroadcast(
        context,
        requestCode,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    private fun getNextOccurrenceCalendar(
        cal: Calendar,
        day: DayOfWeek,
        hour: Int,
        minute: Int,
        now: Long
    ): Calendar = cal.apply {
        set(Calendar.DAY_OF_WEEK, day.toCalendarDay())
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)

        if (timeInMillis <= now) {
            add(Calendar.WEEK_OF_YEAR, 1)
        }
    }

    private fun DayOfWeek.toCalendarDay(): Int = when (this) {
        DayOfWeek.SUNDAY    -> java.util.Calendar.SUNDAY
        DayOfWeek.MONDAY    -> java.util.Calendar.MONDAY
        DayOfWeek.TUESDAY   -> java.util.Calendar.TUESDAY
        DayOfWeek.WEDNESDAY -> java.util.Calendar.WEDNESDAY
        DayOfWeek.THURSDAY  -> java.util.Calendar.THURSDAY
        DayOfWeek.FRIDAY    -> java.util.Calendar.FRIDAY
        DayOfWeek.SATURDAY  -> java.util.Calendar.SATURDAY
    }
}