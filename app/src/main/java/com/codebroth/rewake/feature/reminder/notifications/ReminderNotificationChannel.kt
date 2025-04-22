package com.codebroth.rewake.feature.reminder.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

object ReminderNotificationChannel {

    fun createReminderNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            ReminderNotificationService.REMINDER_CHANNEL_ID,
            ReminderNotificationService.REMINDER_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "This channel is used for bedtime reminders."

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}