package com.codebroth.rewake.reminder.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Query("SELECT * FROM reminders ORDER BY hour, minute")
    fun getAllReminders(): Flow<List<ReminderEntity>>

    @Insert
    suspend fun insert(reminder: ReminderEntity): Int

    @Update
    suspend fun update(reminder: ReminderEntity)

    @Delete
    suspend fun delete(reminder: ReminderEntity)
}