package com.codebroth.rewake.reminder.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.codebroth.rewake.MainActivity
import com.codebroth.rewake.R

class ReminderNotificationService(
    private val context: Context
) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val activityIntent = Intent(context, MainActivity::class.java)
    val activityPendingIntent = PendingIntent.getActivity(
        context,
        0,
        activityIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    PendingIntent.FLAG_IMMUTABLE
                else 0
    )
    fun showReminderNotification(reminderId: Long) {
        val notification = NotificationCompat.Builder(context, REMINDER_CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle("Bedtime Reminder")
            .setContentText("Time to wind down and get ready for bed!")
            .setContentIntent(activityPendingIntent)
            .build()

        notificationManager.notify(
            reminderId.toInt(),
            notification
        )
    }

    fun showTestNotification() {
        showReminderNotification(1)
    }

    companion object {
        const val REMINDER_CHANNEL_ID = "reminder_channel"
        const val REMINDER_CHANNEL_NAME = "Reminder"
        const val EXTRA_REMINDER_ID = "extra_reminder_id"
    }
}