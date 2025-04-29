package com.codebroth.rewake.reminder.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.codebroth.rewake.R

object ReminderNotificationChannel {

    fun createReminderNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            ReminderNotificationService.REMINDER_CHANNEL_ID,
            ReminderNotificationService.REMINDER_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = context.getString(R.string.reminder_channel_description)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}