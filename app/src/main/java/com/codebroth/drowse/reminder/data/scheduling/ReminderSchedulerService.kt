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

package com.codebroth.drowse.reminder.data.scheduling

import com.codebroth.drowse.core.data.scheduling.Scheduler
import com.codebroth.drowse.core.domain.util.TriggerTimeCalculator
import com.codebroth.drowse.reminder.data.Constants.EXTRA_REMINDER_ID
import com.codebroth.drowse.reminder.data.receiver.ReminderReceiver
import com.codebroth.drowse.reminder.data.repository.ReminderRepository
import com.codebroth.drowse.reminder.domain.model.Reminder
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
            putExtra(Scheduler.Companion.EXTRA_ONE_SHOT, reminder.daysOfWeek.isEmpty())
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