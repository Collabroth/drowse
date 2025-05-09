package com.codebroth.rewake.alarm.data.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.codebroth.rewake.R
import com.codebroth.rewake.alarm.AlarmActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AlarmNotificationService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationManager: NotificationManager
) {

    fun showAlarmNotification(alarmId: Int) {
        val notification = NotificationCompat.Builder(context, ALARM_CHANNEL_ID)
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

    fun showFullScreenAlarm(alarmId: Int) {
//        val activityIntent = Intent(context, AlarmActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//            putExtra(EXTRA_ALARM_ID, alarmId)
//        }
//        val fullScreenPendingIntent = PendingIntent.getActivity(
//            context,
//            alarmId,
//            activityIntent,
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
        val fullScreenIntent = Intent(context, AlarmActivity::class.java)
        val fullScreenPendingIntent = PendingIntent.getActivity(
            context,
            alarmId,
            fullScreenIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, ALARM_CHANNEL_ID)
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

    companion object {
        const val ALARM_CHANNEL_ID = "alarm_channel"
        const val ALARM_CHANNEL_NAME = "Alarm"
    }
}