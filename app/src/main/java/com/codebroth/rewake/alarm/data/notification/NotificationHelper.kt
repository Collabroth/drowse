package com.codebroth.rewake.alarm.data.notification

import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import com.codebroth.rewake.R
import com.codebroth.rewake.alarm.data.AlarmConstants.CHANNEL_ID
import com.codebroth.rewake.alarm.data.AlarmConstants.INTENT_ACTION_DISMISS

object NotificationHelper {

    fun buildAlarmNotification(
        context: Context,
        fullScreenPendingIntent: PendingIntent,
        dismissPendingIntent: PendingIntent
    ) = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.baseline_alarm_24)
        .setContentTitle(context.getString(R.string.title_alarm))
        .setContentText(context.getString(R.string.notification_text_swipe_to_dismiss))
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setCategory(NotificationCompat.CATEGORY_ALARM)
        .setOngoing(true)
        .setFullScreenIntent(fullScreenPendingIntent, true)
        .addAction(R.drawable.baseline_alarm_24, INTENT_ACTION_DISMISS, dismissPendingIntent)
        .setDeleteIntent(dismissPendingIntent)
        .build()
}