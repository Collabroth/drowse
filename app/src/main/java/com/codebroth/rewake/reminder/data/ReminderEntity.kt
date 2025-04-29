package com.codebroth.rewake.reminder.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codebroth.rewake.core.data.toDayOfWeekSet
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




