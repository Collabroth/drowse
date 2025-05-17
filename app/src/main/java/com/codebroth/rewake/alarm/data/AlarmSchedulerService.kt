package com.codebroth.rewake.alarm.data

import com.codebroth.rewake.alarm.data.AlarmReceiver.Companion.EXTRA_ALARM_ID
import com.codebroth.rewake.alarm.domain.model.Alarm
import com.codebroth.rewake.core.data.scheduling.Scheduler
import com.codebroth.rewake.core.data.scheduling.Scheduler.Companion.EXTRA_ONE_SHOT
import com.codebroth.rewake.core.data.scheduling.TriggerTimeCalculator
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmSchedulerService @Inject constructor(
    private val scheduler: Scheduler,
    private val repo: AlarmRepository
) {
    suspend fun scheduleAllActiveAlarms() {
        repo.getAllAlarms()
            .first()
            .filter { it.isEnabled }
            .forEach { scheduleNext(it, it.id) }
    }

    fun scheduleNext(alarm: Alarm, alarmId: Int) {
        val zonedDateTime = TriggerTimeCalculator.nextTrigger(
            daysOfWeek = alarm.daysOfWeek,
            hour = alarm.hour,
            minute = alarm.minute
        )
        scheduler.scheduleAt(
            id = alarmId,
            triggerAtMillis = zonedDateTime.toInstant().toEpochMilli(),
            receiver = AlarmReceiver::class.java
        ) {
            putExtra(EXTRA_ALARM_ID, alarmId)
            putExtra(EXTRA_ONE_SHOT, alarm.daysOfWeek.isEmpty())
        }
    }

    suspend fun scheduleNext(alarmId: Int) {
        /**
         * Temporary Solution
         */
        val alarm = repo
            .getAllAlarms()
            .first()
            .first { it.id == alarmId }

        scheduleNext(alarm, alarmId)
    }

    fun cancel(alarmId: Int) {
        scheduler.cancel(alarmId, AlarmReceiver::class.java)
    }

    fun reschedule(alarm: Alarm, alarmId: Int) {
        cancel(alarmId)
        scheduleNext(alarm, alarmId)
    }
}