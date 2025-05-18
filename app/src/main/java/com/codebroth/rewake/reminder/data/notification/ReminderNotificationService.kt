package com.codebroth.rewake.reminder.data.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.codebroth.rewake.MainActivity
import com.codebroth.rewake.R
import com.codebroth.rewake.reminder.data.Constants.REMINDER_NOTIFICATION_CHANNEL_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ReminderNotificationService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationManager: NotificationManager
) {

    val activityIntent = Intent(context, MainActivity::class.java)
    val activityPendingIntent: PendingIntent = PendingIntent.getActivity(
        context,
        0,
        activityIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    PendingIntent.FLAG_IMMUTABLE
                else 0
    )

    fun showReminderNotification(reminderId: Int) {
        val notification = NotificationCompat.Builder(context, REMINDER_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle(context.getString(R.string.reminder_notification_title))
            .setContentText(context.getString(R.string.reminder_notification_description))
            .setContentIntent(activityPendingIntent)
            .build()

        notificationManager.notify(
            reminderId,
            notification
        )
    }

    companion object
}