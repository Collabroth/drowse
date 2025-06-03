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

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.codebroth.drowse.alarm.data.AlarmConstants
import com.codebroth.drowse.alarm.data.AlarmConstants.INTENT_ACTION_DISMISS
import com.codebroth.drowse.alarm.data.notification.AlarmTriggerService
import com.codebroth.drowse.alarm.data.receiver.AlarmReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AlarmAlertHandler @Inject constructor(
    @ApplicationContext private val context: Context
) {

    @Inject
    lateinit var alarmManager: AlarmManager

    val dismissIntent = Intent(context, AlarmTriggerService::class.java).apply {
        action = INTENT_ACTION_DISMISS
    }

    fun dismissAlarm() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(dismissIntent)
        } else {
            context.startService(dismissIntent)
        }
    }

    @SuppressLint("MissingPermission") // used "Use exact alarm" permission
    fun snoozeAlarm() {
        val alarmId = System.currentTimeMillis().toInt()
        val snoozeTimeMillis = System.currentTimeMillis() + 10 * 60 * 1000

        val snoozeIntent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(AlarmConstants.EXTRA_ALARM_ID, alarmId)
            putExtra(AlarmConstants.EXTRA_ALARM_TIME, snoozeTimeMillis)
            putExtra(AlarmConstants.EXTRA_ALARM_LABEL, "Snoozed Alarm")
            putExtra(AlarmConstants.EXTRA_IS_SNOOZE, true)
        }

        val snoozePendingIntent = PendingIntent.getBroadcast(
            context,
            alarmId,
            snoozeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            snoozeTimeMillis,
            snoozePendingIntent
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(dismissIntent)
        } else {
            context.startService(dismissIntent)
        }
    }
}