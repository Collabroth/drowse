package com.codebroth.rewake.alarm.data.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.codebroth.rewake.R
import com.codebroth.rewake.alarm.data.AlarmConstants.CHANNEL_ID
import com.codebroth.rewake.alarm.data.AlarmConstants.CHANNEL_NAME

object AlarmNotificationChannel {

    fun createAlarmNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
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