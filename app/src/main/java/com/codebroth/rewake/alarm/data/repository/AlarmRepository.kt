package com.codebroth.rewake.alarm.data.repository

import com.codebroth.rewake.alarm.domain.model.Alarm
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {

    fun getAllAlarms(): Flow<List<Alarm>>

    suspend fun insertAlarm(alarm: Alarm): Int

    suspend fun updateAlarm(alarm: Alarm)

    suspend fun deleteAlarm(alarm: Alarm)
}