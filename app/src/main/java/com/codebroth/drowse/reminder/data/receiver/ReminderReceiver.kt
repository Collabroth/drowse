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

package com.codebroth.drowse.reminder.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.codebroth.drowse.core.data.scheduling.Scheduler
import com.codebroth.drowse.reminder.data.Constants.EXTRA_REMINDER_ID
import com.codebroth.drowse.reminder.data.notification.ReminderNotificationService
import com.codebroth.drowse.reminder.data.scheduling.ReminderSchedulerService
import com.codebroth.drowse.reminder.domain.model.Reminder
import com.codebroth.drowse.reminder.domain.usecase.DeleteReminderUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ReminderReceiver : BroadcastReceiver() {

    @Inject
    lateinit var reminderNotificationService: ReminderNotificationService

    @Inject
    lateinit var deleteReminderUseCase: DeleteReminderUseCase

    @Inject
    lateinit var reminderSchedulerService: ReminderSchedulerService

    override fun onReceive(context: Context, intent: Intent?) {
        val reminderId = intent?.getIntExtra(EXTRA_REMINDER_ID, -1) ?: return
        if (reminderId != -1) {
            reminderNotificationService
                .showReminderNotification(reminderId)
        }
        val oneShot = intent.getBooleanExtra(Scheduler.Companion.EXTRA_ONE_SHOT, false)
        if (oneShot) {
            CoroutineScope(Dispatchers.IO).launch {
                deleteReminderUseCase(
                    Reminder(
                        id = reminderId,
                        hour = 0,
                        minute = 0,
                        daysOfWeek = emptySet()
                    )
                )
            }
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                reminderSchedulerService.scheduleNext(reminderId)
            }
        }
    }
}