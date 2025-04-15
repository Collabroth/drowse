package com.codebroth.rewake.feature.calculator.domain.util

import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * Utility object for formatting time.
 */
object TimeUtils {

    private val formatter = DateTimeFormatter.ofPattern("h:mm a")

    /**
     * Formats a LocalTime object to a string in the format "h:mm a".
     *
     * @param time The LocalTime object to format.
     * @return The formatted time string.
     */
    fun formatTime(time: LocalTime): String {
        return time.format(formatter)
    }
}