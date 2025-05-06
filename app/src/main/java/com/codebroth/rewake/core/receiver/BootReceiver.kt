package com.codebroth.rewake.core.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.codebroth.rewake.alarm.data.AlarmSchedulerService
import com.codebroth.rewake.reminder.data.notification.ReminderSchedulerService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var reminderSchedulerService: ReminderSchedulerService

    @Inject
    lateinit var alarmSchedulerService: AlarmSchedulerService

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action != Intent.ACTION_BOOT_COMPLETED) return

        val pendingResult = goAsync()

        CoroutineScope(Dispatchers.IO). launch {
            try {
                reminderSchedulerService.scheduleAll()
                alarmSchedulerService.scheduleAllActiveAlarms()
            } finally {
                pendingResult.finish()
            }
        }

    }
}