package com.codebroth.rewake.reminder.data.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.codebroth.rewake.core.data.scheduling.Scheduler.Companion.EXTRA_ONE_SHOT
import com.codebroth.rewake.reminder.domain.model.Reminder
import com.codebroth.rewake.reminder.domain.usecase.DeleteReminderUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ReminderReceiver : BroadcastReceiver() {

    @Inject
    lateinit var reminderNotificationService: ReminderNotificationService

    @Inject
    lateinit var deleteReminderUseCase: DeleteReminderUseCase

    @Inject
    lateinit var reminderSchedulerService: ReminderSchedulerService

    override fun onReceive(context: Context, intent: Intent?) {
        val reminderId = intent?.getIntExtra(EXTRA_REMINDER_ID, -1) ?: return
        if (reminderId != -1) {
            reminderNotificationService
                .showReminderNotification(reminderId)
        }
        val oneShot = intent.getBooleanExtra(EXTRA_ONE_SHOT, false)
        if (oneShot) {
            CoroutineScope(Dispatchers.IO).launch {
                deleteReminderUseCase(
                    Reminder(
                        id = reminderId,
                        hour = 0,
                        minute = 0,
                        daysOfWeek = emptySet()
                    )
                )
            }
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                reminderSchedulerService.scheduleNext(reminderId)
            }
        }
    }

    companion object {
        const val EXTRA_REMINDER_ID = "extra_reminder_id"
    }
}