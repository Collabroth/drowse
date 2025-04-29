package com.codebroth.rewake.reminder.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.codebroth.rewake.reminder.data.ReminderRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject lateinit var scheduler: ReminderAlarmScheduler
    @Inject lateinit var repo: ReminderRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action != Intent.ACTION_BOOT_COMPLETED) return

        CoroutineScope(Dispatchers.IO).launch {
            val allReminders = repo.getAllReminders().first()

            allReminders.forEach { reminder ->
                scheduler.scheduleReminder(reminder.id, reminder)
            }
        }
    }
}