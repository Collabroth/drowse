package com.codebroth.rewake.alarm.data.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.codebroth.rewake.R
import com.codebroth.rewake.alarm.data.Constants.ALARM_CHANNEL_NAME
import com.codebroth.rewake.alarm.data.Constants.ALARM_NOTIFICATION_CHANNEL_ID

object AlarmNotificationChannel {

    fun createAlarmNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            ALARM_NOTIFICATION_CHANNEL_ID,
            ALARM_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            setSound(null, null)
            enableVibration(true)
        }
        channel.description = context.getString(R.string.alarm_channel_description)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}