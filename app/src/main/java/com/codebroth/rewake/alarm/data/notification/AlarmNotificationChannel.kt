package com.codebroth.rewake.alarm.data.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.codebroth.rewake.R

object AlarmNotificationChannel {

    fun createAlarmNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            AlarmNotificationService.ALARM_CHANNEL_ID,
            AlarmNotificationService.ALARM_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = context.getString(R.string.alarm_channel_description)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}