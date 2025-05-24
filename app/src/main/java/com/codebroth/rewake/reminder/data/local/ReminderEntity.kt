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

package com.codebroth.rewake.reminder.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codebroth.rewake.core.domain.util.toDayOfWeekSet
import com.codebroth.rewake.reminder.domain.model.Reminder


@Entity(tableName = "reminders")
data class ReminderEntity(
    val hour: Int,
    val minute: Int,
    val daysOfWeekBitmask: Int,
    val label: String? = null,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)

fun ReminderEntity.toDomainModel(): Reminder =
    Reminder(
        id = id,
        hour = hour,
        minute = minute,
        daysOfWeek = daysOfWeekBitmask.toDayOfWeekSet(),
        label = label
    )




