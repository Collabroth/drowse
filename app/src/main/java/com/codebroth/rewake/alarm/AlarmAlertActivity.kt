package com.codebroth.rewake.alarm

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.codebroth.rewake.alarm.data.AlarmConstants.EXTRA_ALARM_ID
import com.codebroth.rewake.alarm.data.AlarmConstants.EXTRA_ALARM_LABEL
import com.codebroth.rewake.alarm.data.AlarmConstants.EXTRA_ALARM_TIME
import com.codebroth.rewake.alarm.data.AlarmConstants.INTENT_ACTION_DISMISS
import com.codebroth.rewake.alarm.data.notification.AlarmTriggerService
import com.codebroth.rewake.alarm.ui.AlarmAlertContent
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.time.ZoneId

private const val DEBUG_TAG = "AlarmActivity"

@AndroidEntryPoint
class AlarmAlertActivity : ComponentActivity() {

    private var alarmId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        alarmId = intent.getIntExtra(EXTRA_ALARM_ID, -1)

        val timeInMillis = intent.getLongExtra(EXTRA_ALARM_TIME, System.currentTimeMillis())
        val alarmLabel = intent.getStringExtra(EXTRA_ALARM_LABEL).orEmpty()
        val localTime = Instant
            .ofEpochMilli(timeInMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalTime()

        setWindowFlags()

        setContent {
            AlarmAlertContent(
                onDismiss = { dismissAlarm() },
                onSnooze = { snoozeAlarm() },
                alarmTime = localTime,
                alarmLabel = if (alarmLabel.isNotBlank()) alarmLabel else null
            )
        }
    }

    private fun setWindowFlags() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
            Log.d(DEBUG_TAG, "window flags: \n${window.attributes.flags}")
        } else {
            @Suppress("DEPRECATION")
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private fun dismissAlarm() {
        Intent(this, AlarmTriggerService::class.java)
            .apply { action = INTENT_ACTION_DISMISS }
            .let(::startService)
        finish()
    }

    private fun snoozeAlarm() {
        //TODO: implement snooze
        finish()
    }
}