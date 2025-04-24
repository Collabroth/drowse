package com.codebroth.rewake.reminder.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.codebroth.rewake.reminder.notifications.ReminderNotificationService.Companion.EXTRA_REMINDER_ID

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val reminderId = intent?.getLongExtra(EXTRA_REMINDER_ID, -1L) ?: return
        if (reminderId != -1L) {
            ReminderNotificationService(context)
                .showReminderNotification(reminderId)
        }
    }
}