package com.codebroth.rewake.alarm

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
import com.codebroth.rewake.alarm.ui.AlarmAlertContent
import com.codebroth.rewake.alarm.ui.AlarmAlertViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.time.ZoneId

private const val DEBUG_TAG = "AlarmActivity"

/**
 * The activity that shows the alarm alert screen when an alarm goes off.
 *
 * This activity is launched by the AlarmTriggerService when an alarm goes off.
 * It shows the alarm time and label, and allows the user to dismiss or snooze the alarm.
 */
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

    /**
     * Set the window flags to show the alarm alert screen over the lock screen and turn on the screen.
     */
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