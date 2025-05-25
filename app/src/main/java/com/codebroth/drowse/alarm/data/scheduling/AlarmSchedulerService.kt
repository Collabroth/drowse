/*
 *
 *    Copyright 2025 Jayman Rana
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.codebroth.drowse.alarm.data.scheduling

import com.codebroth.drowse.alarm.data.AlarmConstants
import com.codebroth.drowse.alarm.data.receiver.AlarmReceiver
import com.codebroth.drowse.alarm.data.repository.AlarmRepository
import com.codebroth.drowse.alarm.domain.model.Alarm
import com.codebroth.drowse.core.data.scheduling.Scheduler
import com.codebroth.drowse.core.domain.util.TriggerTimeCalculator
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
            putExtra(AlarmConstants.EXTRA_ALARM_ID, alarmId)
            putExtra(AlarmConstants.EXTRA_ALARM_TIME, triggerAtMillis)
            putExtra(AlarmConstants.EXTRA_ALARM_LABEL, alarm.label.orEmpty())
            putExtra(Scheduler.Companion.EXTRA_ONE_SHOT, alarm.daysOfWeek.isEmpty())
        }
    }

    suspend fun scheduleNext(alarmId: Int) {

        val alarm = repo.getAlarmById(alarmId).first()
            ?: throw IllegalArgumentException("No alarm found with id=$alarmId")
        scheduleNext(alarm, alarmId)
    }

    fun cancel(alarmId: Int) {
        scheduler.cancel(alarmId, AlarmReceiver::class.java)
    }

    suspend fun cancelAllActive() {
        val activeAlarms = repo.getAllAlarms()
            .first()
            .filter { it.isEnabled }

        activeAlarms.forEach { alarm ->
            cancel(alarm.id)
            repo.updateAlarm(alarm.copy(isEnabled = false))
        }
    }

    fun reschedule(alarm: Alarm, alarmId: Int) {
        cancel(alarmId)
        scheduleNext(alarm, alarmId)
    }
}