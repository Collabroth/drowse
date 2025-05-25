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

package com.codebroth.drowse.core.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.codebroth.drowse.alarm.data.scheduling.AlarmSchedulerService
import com.codebroth.drowse.reminder.data.scheduling.ReminderSchedulerService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var reminderSchedulerService: ReminderSchedulerService

    @Inject
    lateinit var alarmSchedulerService: AlarmSchedulerService

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action != Intent.ACTION_BOOT_COMPLETED) return

        val pendingResult = goAsync()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                reminderSchedulerService.scheduleAll()
                alarmSchedulerService.scheduleAllActiveAlarms()
            } finally {
                pendingResult.finish()
            }
        }

    }
}