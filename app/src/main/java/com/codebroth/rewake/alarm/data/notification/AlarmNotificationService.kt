package com.codebroth.rewake.alarm.data.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.codebroth.rewake.R
import com.codebroth.rewake.alarm.AlarmActivity
import com.codebroth.rewake.alarm.data.Constants.ALARM_NOTIFICATION_CHANNEL_ID
import com.codebroth.rewake.alarm.data.Constants.EXTRA_ALARM_ID
import com.codebroth.rewake.alarm.data.Constants.EXTRA_ALARM_LABEL
import com.codebroth.rewake.alarm.data.Constants.EXTRA_ALARM_TIME
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AlarmNotificationService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationManager: NotificationManager
) {

    fun showAlarmNotification(alarmId: Int) {
        val notification = NotificationCompat.Builder(context, ALARM_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_alarm_24)
            .setContentTitle("Rewake")
            .setContentText("Placeholder")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .build()

        notificationManager.notify(
            alarmId,
            notification
        )
    }

    fun showFullScreenAlarm(alarmId: Int, timeInMillis: Long, label: String) {
        val activityIntent = Intent(context, AlarmActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(EXTRA_ALARM_ID, alarmId)
            putExtra(EXTRA_ALARM_TIME, timeInMillis)
            putExtra(EXTRA_ALARM_LABEL, label)
        }

        val fullScreenPendingIntent = PendingIntent.getActivity(
            context,
            alarmId,
            activityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, ALARM_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_alarm_24)
            .setContentTitle("Alarm")
            .setContentText("Swipe to dismiss")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setOngoing(true)
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .build()

        notificationManager.notify(alarmId, notification)
    }
}