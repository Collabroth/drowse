package com.codebroth.rewake.alarm.data

import com.codebroth.rewake.alarm.data.AlarmReceiver.Companion.EXTRA_ALARM_ID
import com.codebroth.rewake.alarm.domain.model.Alarm
import com.codebroth.rewake.core.data.scheduling.Scheduler
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
            .forEach { schedule(it, it.id) }
    }

    fun schedule(alarm: Alarm, alarmId: Int) {
        scheduler.schedule(
            id = alarmId,
            daysOfWeek = alarm.daysOfWeek,
            hour = alarm.hour,
            minute = alarm.minute,
            receiver = AlarmReceiver::class.java
        ) {
            putExtra(EXTRA_ALARM_ID, alarmId)
        }
    }

    fun cancel(alarmId: Int) {
        scheduler.cancel(alarmId, AlarmReceiver::class.java)
    }

    fun reschedule(alarm: Alarm, alarmId: Int) {
        cancel(alarmId)
        schedule(alarm, alarmId)
    }
}