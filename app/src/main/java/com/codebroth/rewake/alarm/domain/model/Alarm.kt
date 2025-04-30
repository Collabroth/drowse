package com.codebroth.rewake.alarm.domain.model

import com.codebroth.rewake.alarm.data.AlarmEntity
import com.codebroth.rewake.core.data.toBitmask
import java.time.DayOfWeek

data class Alarm(
    val id: Int = 0,
    val hour: Int,
    val minute: Int,
    val daysOfWeek: Set<DayOfWeek>,
    val label: String? = null,
    val isEnabled: Boolean = true
)

fun Alarm.fromDomainModel(): AlarmEntity =
    AlarmEntity(
        id = id,
        hour = hour,
        minute = minute,
        daysOfWeekBitmask = daysOfWeek.toBitmask(),
        label = label,
        isEnabled = isEnabled
    )