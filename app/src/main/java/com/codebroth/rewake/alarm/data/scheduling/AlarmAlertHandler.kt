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

package com.codebroth.rewake.alarm.data.scheduling

import android.content.Context
import android.content.Intent
import android.os.Build
import com.codebroth.rewake.alarm.data.AlarmConstants.INTENT_ACTION_DISMISS
import com.codebroth.rewake.alarm.data.notification.AlarmTriggerService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AlarmAlertHandler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun dismissAlarm() {
        val intent = Intent(context, AlarmTriggerService::class.java).apply {
            action = INTENT_ACTION_DISMISS
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }

    fun snoozeAlarm() {
        TODO()
    }
}