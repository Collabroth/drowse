package com.codebroth.rewake.alarm.data.notification

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import com.codebroth.rewake.alarm.AlarmActivity
import com.codebroth.rewake.alarm.data.AlarmConstants.EXTRA_ALARM_ID
import com.codebroth.rewake.alarm.data.AlarmConstants.EXTRA_ALARM_LABEL
import com.codebroth.rewake.alarm.data.AlarmConstants.EXTRA_ALARM_TIME
import com.codebroth.rewake.alarm.data.AlarmConstants.INTENT_ACTION_DISMISS

class AlarmTriggerService : Service() {

    private lateinit var ringtone: Ringtone

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when (intent?.action) {
            INTENT_ACTION_DISMISS -> {
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
                return START_NOT_STICKY
            }
        }

        val alarmId = intent?.getIntExtra(EXTRA_ALARM_ID, -1) ?: return START_NOT_STICKY
        val timeInMillis = intent.getLongExtra(EXTRA_ALARM_TIME, System.currentTimeMillis())
        val label = intent.getStringExtra(EXTRA_ALARM_LABEL).orEmpty()

        val fullScreenIntent = Intent(this, AlarmActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            putExtra(EXTRA_ALARM_ID, alarmId)
            putExtra(EXTRA_ALARM_TIME, timeInMillis)
            putExtra(EXTRA_ALARM_LABEL, label)
        }
        val fullScreenPendingIntent = PendingIntent.getActivity(
            this,
            alarmId,
            fullScreenIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val dismissIntent = Intent(this, AlarmTriggerService::class.java).apply {
            action = INTENT_ACTION_DISMISS
        }
        val dismissPendingIntent = PendingIntent.getService(
            this,
            alarmId,
            dismissIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationHelper
            .buildAlarmNotification(this, fullScreenPendingIntent, dismissPendingIntent)

        val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(this, alarmUri).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) isLooping = true
            play()
        }

        startForeground(alarmId, notification)

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        if (::ringtone.isInitialized && ringtone.isPlaying) {
            ringtone.stop()
        }
        super.onDestroy()
    }

    override fun onBind(intent: Intent?) = null
}