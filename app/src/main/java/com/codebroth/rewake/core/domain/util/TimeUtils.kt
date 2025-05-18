package com.codebroth.rewake.core.domain.util

import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * Utility object for formatting time.
 */
object TimeUtils {

    private val formatter = DateTimeFormatter.ofPattern("h:mm a")

    private val WEEKEND = setOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)
    private val WEEKDAYS = DayOfWeek.entries.toSet() - WEEKEND

    /**
     * Formats a LocalTime object to a string in the format "h:mm a".
     *
     * @param time The LocalTime object to format.
     * @return The formatted time string.
     */
    fun formatTime(time: LocalTime): String {
        return time.format(formatter)
    }

    fun getTimeFromHourMinute(hour: Int, minute: Int): LocalTime {
        return LocalTime.of(
            hour,
            minute
        )
    }

    fun summarizeSelectedDaysOfWeek(days: Set<DayOfWeek>): String = when {
        days.isEmpty() -> "None"
        days.size == 7 -> "Everyday"
        days == WEEKDAYS -> "Weekdays"
        days == WEEKEND -> "Weekend"
        else -> {
            days
                .sortedBy { it.value }
                .joinToString { day ->
                    day
                        .name
                        .take(3)
                        .lowercase()
                        .replaceFirstChar { it.uppercase() }
                }
        }
    }
}