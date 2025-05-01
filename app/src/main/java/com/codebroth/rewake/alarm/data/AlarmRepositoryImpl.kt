package com.codebroth.rewake.alarm.data

import com.codebroth.rewake.alarm.domain.model.Alarm
import com.codebroth.rewake.alarm.domain.model.fromDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmRepositoryImpl @Inject constructor(
    private val dao: AlarmDao
): AlarmRepository {
    override fun getAllAlarms(): Flow<List<Alarm>> {
        return dao.getAllAlarms()
            .map { list -> list.map { it.toDomainModel() } }
    }

    override suspend fun insertAlarm(alarm: Alarm): Int {
        val rowId = dao.insert(alarm.fromDomainModel())
        return rowId.toInt()
    }

    override suspend fun updateAlarm(alarm: Alarm) {
        dao.update(alarm.fromDomainModel())
    }

    override suspend fun deleteAlarm(alarm: Alarm) {
        dao.delete(alarm.fromDomainModel())
    }
}