package com.codebroth.rewake.alarm.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.codebroth.rewake.alarm.data.notification.AlarmNotificationService
import com.codebroth.rewake.alarm.data.Constants.EXTRA_ALARM_ID
import com.codebroth.rewake.alarm.data.Constants.EXTRA_ALARM_LABEL
import com.codebroth.rewake.alarm.data.Constants.EXTRA_ALARM_TIME
import com.codebroth.rewake.alarm.domain.model.Alarm
import com.codebroth.rewake.alarm.domain.usecase.GetAllAlarmsUseCase
import com.codebroth.rewake.alarm.domain.usecase.UpdateAlarmUseCase
import com.codebroth.rewake.core.data.scheduling.Scheduler.Companion.EXTRA_ONE_SHOT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmNotificationService: AlarmNotificationService

    @Inject
    lateinit var getAllAlarmsUseCase: GetAllAlarmsUseCase

    @Inject
    lateinit var updateAlarmUseCase: UpdateAlarmUseCase

    @Inject
    lateinit var alarmSchedulerService: AlarmSchedulerService

    override fun onReceive(context: Context, intent: Intent?) {
        val alarmId = intent?.getIntExtra(EXTRA_ALARM_ID, -1) ?: return
        val timeInMillis = intent.getLongExtra(EXTRA_ALARM_TIME, System.currentTimeMillis())
        val label = intent.getStringExtra(EXTRA_ALARM_LABEL).orEmpty()

        if (alarmId != -1) {
            alarmNotificationService
                .showFullScreenAlarm(alarmId, timeInMillis, label)
        }
        val oneShot = intent.getBooleanExtra(EXTRA_ONE_SHOT, false)
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