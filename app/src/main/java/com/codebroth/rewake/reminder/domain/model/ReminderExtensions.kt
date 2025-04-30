package com.codebroth.rewake.reminder.domain.model

import com.codebroth.rewake.core.domain.util.TimeUtils
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Reminder.formattedTime(local: Locale = Locale.getDefault()): String {
    val time = LocalTime.of(hour, minute)
    return TimeUtils.formatTime(time)
}