package com.codebroth.rewake.reminder.data.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.codebroth.rewake.R
import com.codebroth.rewake.reminder.data.Constants.REMINDER_CHANNEL_NAME
import com.codebroth.rewake.reminder.data.Constants.REMINDER_NOTIFICATION_CHANNEL_ID

object ReminderNotificationChannel {

    fun createReminderNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            REMINDER_NOTIFICATION_CHANNEL_ID,
            REMINDER_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = context.getString(R.string.reminder_channel_description)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}