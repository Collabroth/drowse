package com.codebroth.drowse.reminder.data.repository

import com.codebroth.drowse.reminder.domain.model.Reminder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeReminderRepository : ReminderRepository {

    private val reminders = mutableListOf<Reminder>()

    override fun getAllReminders(): Flow<List<Reminder>> {
        return flow { emit(reminders) }
    }

    override suspend fun insertReminder(reminder: Reminder): Int {
        val assignedId =
            if (reminder.id != 0) reminder.id else 50 // simulating ID generation by database
        val insertedReminder = reminder.copy(id = assignedId)
        reminders.add(insertedReminder)
        return assignedId
    }

    override suspend fun updateReminder(reminder: Reminder) {
        val index = reminders.indexOfFirst { it.id == reminder.id }
        if (index != -1) {
            reminders[index] = reminder
        }
    }

    override suspend fun deleteReminder(reminder: Reminder) {
        reminders.remove(reminder)
    }
}