package com.codebroth.rewake.reminder.domain.model

import com.codebroth.rewake.reminder.data.ReminderEntity
import com.codebroth.rewake.reminder.data.toBitmask
import java.time.DayOfWeek

data class Reminder(
    val id: Long = 0,
    val hour: Int,
    val minute: Int,
    val daysOfWeek: Set<DayOfWeek>,
    val label: String? = null
)

fun Reminder.fromDomainModel(): ReminderEntity =
    ReminderEntity(
        id = if (id.toInt() == 0) 0 else id,
        hour = hour,
        minute = minute,
        daysOfWeekBitmask = daysOfWeek.toBitmask(),
        label = label
    )
