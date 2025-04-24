package com.codebroth.rewake.reminder.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codebroth.rewake.reminder.domain.model.Reminder


@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val hour: Int,
    val minute: Int,
    val daysOfWeekBitmask: Int,
    val label: String? = null
)

fun ReminderEntity.toDomainModel(): Reminder =
    Reminder(
        id = id,
        hour = hour,
        minute = minute,
        daysOfWeek = daysOfWeekBitmask.toDayOfWeekSet(),
        label = label
    )




