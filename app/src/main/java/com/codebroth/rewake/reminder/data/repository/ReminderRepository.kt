package com.codebroth.rewake.reminder.data.repository

import com.codebroth.rewake.reminder.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {

    fun getAllReminders(): Flow<List<Reminder>>

    suspend fun insertReminder(reminder: Reminder): Int

    suspend fun updateReminder(reminder: Reminder)

    suspend fun deleteReminder(reminder: Reminder)
}