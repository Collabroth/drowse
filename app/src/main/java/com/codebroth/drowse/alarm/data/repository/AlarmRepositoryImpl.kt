/*
 *
 *    Copyright 2025 Jayman Rana
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.codebroth.drowse.alarm.data.repository

import com.codebroth.drowse.alarm.data.local.AlarmDao
import com.codebroth.drowse.alarm.data.local.toDomainModel
import com.codebroth.drowse.alarm.domain.model.Alarm
import com.codebroth.drowse.alarm.domain.model.fromDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmRepositoryImpl @Inject constructor(
    private val dao: AlarmDao
) : AlarmRepository {
    override fun getAllAlarms(): Flow<List<Alarm>> {
        return dao.getAllAlarms()
            .map { list -> list.map { it.toDomainModel() } }
    }

    override fun getAlarmById(id: Int): Flow<Alarm?> {
        return dao.getAlarmById(id)
            .map { it.toDomainModel() }
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