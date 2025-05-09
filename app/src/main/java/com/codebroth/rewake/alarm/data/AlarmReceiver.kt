package com.codebroth.rewake.alarm.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.codebroth.rewake.alarm.data.notification.AlarmNotificationService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmNotificationService: AlarmNotificationService

    override fun onReceive(context: Context, intent: Intent?) {
        val alarmId = intent?.getIntExtra(EXTRA_ALARM_ID, -1) ?: return

        if (alarmId != -1) {
            alarmNotificationService
                .showFullScreenAlarm(alarmId)
        }
    }

    companion object {
        const val EXTRA_ALARM_ID = "extra_alarm_id"
    }

}