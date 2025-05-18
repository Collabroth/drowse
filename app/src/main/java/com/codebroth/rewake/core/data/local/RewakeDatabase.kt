package com.codebroth.rewake.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codebroth.rewake.alarm.data.local.AlarmDao
import com.codebroth.rewake.alarm.data.local.AlarmEntity
import com.codebroth.rewake.reminder.data.local.ReminderDao
import com.codebroth.rewake.reminder.data.local.ReminderEntity

@Database(
    entities = [ReminderEntity::class, AlarmEntity::class],
    version = 2,
    exportSchema = true
)
abstract class RewakeDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao
    abstract fun alarmDao(): AlarmDao
}