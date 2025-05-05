package com.codebroth.rewake.reminder.data.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.codebroth.rewake.reminder.data.notifications.ReminderNotificationService.Companion.EXTRA_REMINDER_ID

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val reminderId = intent?.getIntExtra(EXTRA_REMINDER_ID, -1) ?: return
        if (reminderId != -1) {
            ReminderNotificationService(context)
                .showReminderNotification(reminderId)
        }
    }
}