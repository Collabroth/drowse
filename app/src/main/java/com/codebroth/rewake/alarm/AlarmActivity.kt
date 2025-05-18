package com.codebroth.rewake.alarm

import android.app.NotificationManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.codebroth.rewake.alarm.data.AlarmReceiver
import com.codebroth.rewake.alarm.data.Constants.EXTRA_ALARM_ID
import com.codebroth.rewake.alarm.data.Constants.EXTRA_ALARM_LABEL
import com.codebroth.rewake.alarm.data.Constants.EXTRA_ALARM_TIME
import com.codebroth.rewake.alarm.ui.FullScreenAlarmNotification
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

private const val DEBUG_TAG = "AlarmActivity"

@AndroidEntryPoint
class AlarmActivity : ComponentActivity() {

    private var alarmId: Int = -1
    private lateinit var ringtone: Ringtone

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        alarmId = intent.getIntExtra(EXTRA_ALARM_ID, -1)

        val timeInMillis = intent.getLongExtra(EXTRA_ALARM_TIME, System.currentTimeMillis())
        val alarmLabel = intent.getStringExtra(EXTRA_ALARM_LABEL).orEmpty()

        val alarmTime = SimpleDateFormat("hh:mm a", Locale.getDefault())
            .format(Date(timeInMillis))

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

        val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(this, alarmUri)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ringtone.isLooping = true
            ringtone.play()
        }


        setContent {
            FullScreenAlarmNotification(
                alarmTime = alarmTime,
                onDismiss = { dismissAlarm() },
                onSnooze = { snoozeAlarm() },
                alarmLabel = if (alarmLabel.isNotBlank()) alarmLabel else null
            )
        }
    }

    private fun dismissAlarm() {
        if (ringtone.isPlaying) ringtone.stop()
        notificationManager.cancel(alarmId)
        finish()
    }

    private fun snoozeAlarm() {
        if (ringtone.isPlaying) ringtone.stop()
        //TODO: implement snooze
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::ringtone.isInitialized && ringtone.isPlaying) {
            ringtone.stop()
        }
    }
}