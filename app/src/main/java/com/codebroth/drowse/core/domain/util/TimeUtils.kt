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

package com.codebroth.drowse.core.domain.util

import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * Utility object for formatting time.
 */
object TimeUtils {

    private val WEEKEND = setOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)
    private val WEEKDAYS = DayOfWeek.entries.toSet() - WEEKEND

    /**
     * Formats a LocalTime object to a string in the format "h:mm a".
     *
     * @param time The LocalTime object to format.
     * @return The formatted time string.
     */
    fun formatTime(time: LocalTime, is24Hour: Boolean = false): String {
        val pattern = if (is24Hour) "HH:mm" else "h:mm a"
        return time.format(DateTimeFormatter.ofPattern(pattern))
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