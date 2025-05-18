package com.codebroth.rewake.alarm.data.scheduling

import com.codebroth.rewake.alarm.data.Constants
import com.codebroth.rewake.alarm.data.receiver.AlarmReceiver
import com.codebroth.rewake.alarm.data.repository.AlarmRepository
import com.codebroth.rewake.alarm.domain.model.Alarm
import com.codebroth.rewake.core.data.scheduling.Scheduler
import com.codebroth.rewake.core.domain.util.TriggerTimeCalculator
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
        val triggerAtMillis = TriggerTimeCalculator.nextTrigger(
            daysOfWeek = alarm.daysOfWeek,
            hour = alarm.hour,
            minute = alarm.minute
        )
            .toInstant()
            .toEpochMilli()
        scheduler.scheduleAt(
            id = alarmId,
            triggerAtMillis = triggerAtMillis,
            receiver = AlarmReceiver::class.java
        ) {
            putExtra(Constants.EXTRA_ALARM_ID, alarmId)
            putExtra(Constants.EXTRA_ALARM_TIME, triggerAtMillis)
            putExtra(Constants.EXTRA_ALARM_LABEL, alarm.label.orEmpty())
            putExtra(Scheduler.Companion.EXTRA_ONE_SHOT, alarm.daysOfWeek.isEmpty())
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