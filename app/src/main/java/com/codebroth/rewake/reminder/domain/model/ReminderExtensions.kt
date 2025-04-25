package com.codebroth.rewake.reminder.domain.model

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Reminder.formattedTime(local: Locale = Locale.getDefault()): String {
    val time = LocalTime.of(hour, minute)
    val formatter = DateTimeFormatter.ofPattern("h:mm a", local)
    return time.format(formatter)
}