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

package com.codebroth.drowse.reminder.data.repository

import com.codebroth.drowse.reminder.data.local.ReminderDao
import com.codebroth.drowse.reminder.data.local.toDomainModel
import com.codebroth.drowse.reminder.domain.model.Reminder
import com.codebroth.drowse.reminder.domain.model.fromDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReminderRepositoryImpl @Inject constructor(
    private val dao: ReminderDao
) : ReminderRepository {

    override fun getAllReminders(): Flow<List<Reminder>> =
        dao.getAllReminders()
            .map { list -> list.map { it.toDomainModel() } }

    override suspend fun insertReminder(reminder: Reminder): Int {
        val rowId = dao.insert(reminder.fromDomainModel())
        return rowId.toInt()
    }

    override suspend fun updateReminder(reminder: Reminder) {
        dao.update(reminder.fromDomainModel())
    }

    override suspend fun deleteReminder(reminder: Reminder) {
        dao.delete(reminder.fromDomainModel())
    }

}