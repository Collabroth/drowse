package com.codebroth.rewake.reminder.data

import com.codebroth.rewake.reminder.domain.model.Reminder
import com.codebroth.rewake.reminder.domain.model.fromDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReminderRepositoryImpl @Inject constructor(
    private val dao: ReminderDao
): ReminderRepository {

    override fun getAllReminders(): Flow<List<Reminder>> =
        dao.getAllReminders()
            .map { list -> list.map { it.toDomainModel() } }

    override suspend fun insertReminder(reminder: Reminder): Long {
        val rowId = dao.insert(reminder.fromDomainModel())
        return rowId
    }

    override suspend fun updateReminder(reminder: Reminder) {
        dao.update(reminder.fromDomainModel())
    }

    override suspend fun deleteReminder(reminder: Reminder) {
        dao.delete(reminder.fromDomainModel())
    }

}