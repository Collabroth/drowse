package com.codebroth.rewake.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codebroth.rewake.reminder.data.ReminderDao
import com.codebroth.rewake.reminder.data.ReminderEntity

@Database(
    entities = [ReminderEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RewakeDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao
}