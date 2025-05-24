package com.codebroth.rewake.alarm.domain.model

import com.codebroth.rewake.core.domain.util.TimeUtils
import java.time.LocalTime

fun Alarm.formattedTime(is24Hour: Boolean = false): String {
    val time = LocalTime.of(hour, minute)
    return TimeUtils.formatTime(time, is24Hour)
}