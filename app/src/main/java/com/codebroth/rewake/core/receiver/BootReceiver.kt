package com.codebroth.rewake.core.receiver

import android.content.BroadcastReceiver
import com.codebroth.rewake.reminder.data.notifications.ReminderAlarmScheduler
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var scheduler: ReminderAlarmScheduler

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action != Intent.ACTION_BOOT_COMPLETED) return


        // Schedule all reminders on boot
        scheduler.scheduleAllReminders()
    }
}