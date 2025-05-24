package com.codebroth.rewake.alarm

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.codebroth.rewake.alarm.data.AlarmConstants.EXTRA_ALARM_ID
import com.codebroth.rewake.alarm.data.AlarmConstants.EXTRA_ALARM_LABEL
import com.codebroth.rewake.alarm.data.AlarmConstants.EXTRA_ALARM_TIME
import com.codebroth.rewake.alarm.data.AlarmConstants.INTENT_ACTION_DISMISS
import com.codebroth.rewake.alarm.data.notification.AlarmTriggerService
import com.codebroth.rewake.alarm.ui.AlarmAlertContent
import com.codebroth.rewake.alarm.ui.AlarmAlertViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.time.ZoneId

private const val DEBUG_TAG = "AlarmActivity"

@AndroidEntryPoint
class AlarmAlertActivity : ComponentActivity() {

    private var alarmId: Int = -1

    private val viewModel: AlarmAlertViewModel by viewModels()

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
                alarmTime = localTime,
                alarmLabel = if (alarmLabel.isNotBlank()) alarmLabel else null
            )
        }

        viewModel.shouldFinish.observe(this) { shouldFinish ->
            if (shouldFinish) finish()
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
}