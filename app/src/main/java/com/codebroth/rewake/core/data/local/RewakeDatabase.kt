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