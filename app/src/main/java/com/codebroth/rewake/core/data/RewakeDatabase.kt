package com.codebroth.rewake.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codebroth.rewake.alarm.data.AlarmDao
import com.codebroth.rewake.alarm.data.AlarmEntity
import com.codebroth.rewake.reminder.data.ReminderDao
import com.codebroth.rewake.reminder.data.ReminderEntity

@Database(
    entities = [ReminderEntity::class, AlarmEntity::class],
    version = 2,
    exportSchema = true
)
abstract class RewakeDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao
    abstract fun alarmDao(): AlarmDao
}