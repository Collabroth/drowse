package com.codebroth.drowse.alarm.data.repository

import com.codebroth.drowse.alarm.domain.model.Alarm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeAlarmRepository : AlarmRepository {

    private val alarms = mutableListOf<Alarm>()

    override fun getAllAlarms(): Flow<List<Alarm>> {
        return flow { emit(alarms) }
    }

    override fun getAlarmById(id: Int): Flow<Alarm?> {
        return flow { emit(alarms.find { it.id == id }) }
    }

    override suspend fun insertAlarm(alarm: Alarm): Int {
        val assignedId = if (alarm.id != 0) alarm.id else 50 // simulating ID generation by database
        val insertedAlarm = alarm.copy(id = assignedId)
        alarms.add(insertedAlarm)
        return assignedId
    }

    override suspend fun updateAlarm(alarm: Alarm) {
        val index = alarms.indexOfFirst { it.id == alarm.id }
        if (index != -1) {
            alarms[index] = alarm
        }
    }

    override suspend fun deleteAlarm(alarm: Alarm) {
        alarms.remove(alarm)
    }
}