package com.codebroth.rewake.alarm.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.codebroth.rewake.alarm.data.AlarmConstants
import com.codebroth.rewake.alarm.data.notification.AlarmTriggerService
import com.codebroth.rewake.alarm.data.scheduling.AlarmSchedulerService
import com.codebroth.rewake.alarm.domain.model.Alarm
import com.codebroth.rewake.alarm.domain.usecase.GetAllAlarmsUseCase
import com.codebroth.rewake.alarm.domain.usecase.UpdateAlarmUseCase
import com.codebroth.rewake.core.data.scheduling.Scheduler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * TODO: Write a getAlarmByIdUseCase
 */
@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var getAllAlarmsUseCase: GetAllAlarmsUseCase

    @Inject
    lateinit var updateAlarmUseCase: UpdateAlarmUseCase

    @Inject
    lateinit var alarmSchedulerService: AlarmSchedulerService

    override fun onReceive(context: Context, intent: Intent?) {

        val alarmId = intent?.getIntExtra(AlarmConstants.EXTRA_ALARM_ID, -1) ?: return
        val timeInMillis =
            intent.getLongExtra(AlarmConstants.EXTRA_ALARM_TIME, System.currentTimeMillis())
        val label = intent.getStringExtra(AlarmConstants.EXTRA_ALARM_LABEL).orEmpty()

        Intent(context, AlarmTriggerService::class.java).also {
            it.putExtra(AlarmConstants.EXTRA_ALARM_ID, alarmId)
            it.putExtra(AlarmConstants.EXTRA_ALARM_TIME, timeInMillis)
            it.putExtra(AlarmConstants.EXTRA_ALARM_LABEL, label)
            context.startForegroundService(it)
        }
        val oneShot = intent.getBooleanExtra(Scheduler.Companion.EXTRA_ONE_SHOT, false)
        if (oneShot) {
            CoroutineScope(Dispatchers.IO).launch {
                val alarms: Flow<List<Alarm>> = getAllAlarmsUseCase()

                val alarm = alarms
                    .first()
                    .first { it.id == alarmId }

                updateAlarmUseCase(alarm.copy(isEnabled = false))
            }
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                alarmSchedulerService.scheduleNext(alarmId)
            }
        }
    }
}