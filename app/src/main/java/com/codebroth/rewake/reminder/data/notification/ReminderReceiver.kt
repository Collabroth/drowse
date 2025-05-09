package com.codebroth.rewake.reminder.data.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReminderReceiver : BroadcastReceiver() {

    @Inject
    lateinit var  reminderNotificationService: ReminderNotificationService

    override fun onReceive(context: Context, intent: Intent?) {
        val reminderId = intent?.getIntExtra(EXTRA_REMINDER_ID, -1) ?: return
        if (reminderId != -1) {
            reminderNotificationService
                .showReminderNotification(reminderId)
        }
    }

    companion object {
        const val EXTRA_REMINDER_ID = "extra_reminder_id"
    }
}