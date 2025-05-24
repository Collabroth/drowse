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