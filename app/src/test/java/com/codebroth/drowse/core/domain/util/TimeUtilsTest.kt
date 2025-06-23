package com.codebroth.drowse.core.domain.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalTime

class TimeUtilsTest {

    @Test
    fun `formatTime returns correct 12-hour format`() {
        val time = LocalTime.of(9, 5)
        val formatted = TimeUtils.formatTime(time, is24Hour = false)
        assertThat(formatted).isEqualTo("9:05 AM")
    }

    @Test
    fun `formatTime returns correct 24-hour format`() {
        val time = LocalTime.of(21, 30)
        val formatted = TimeUtils.formatTime(time, is24Hour = true)
        assertThat(formatted).isEqualTo("21:30")
    }

    @Test
    fun `getTimeFromHourMinute returns correct LocalTime`() {
        val time = TimeUtils.getTimeFromHourMinute(14, 45)
        assertThat(time).isEqualTo(LocalTime.of(14, 45))
    }

    @Test
    fun `summarizeSelectedDaysOfWeek returns None for empty set`() {
        val summary = TimeUtils.summarizeSelectedDaysOfWeek(emptySet())
        assertThat(summary).isEqualTo("None")
    }

    @Test
    fun `summarizeSelectedDaysOfWeek returns Everyday for all days`() {
        val summary = TimeUtils.summarizeSelectedDaysOfWeek(DayOfWeek.entries.toSet())
        assertThat(summary).isEqualTo("Everyday")
    }

    @Test
    fun `summarizeSelectedDaysOfWeek returns Weekdays for weekdays`() {
        val weekdays = DayOfWeek.entries.toSet() - setOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)
        val summary = TimeUtils.summarizeSelectedDaysOfWeek(weekdays)
        assertThat(summary).isEqualTo("Weekdays")
    }

    @Test
    fun `summarizeSelectedDaysOfWeek returns Weekend for weekend`() {
        val weekend = setOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)
        val summary = TimeUtils.summarizeSelectedDaysOfWeek(weekend)
        assertThat(summary).isEqualTo("Weekend")
    }

    @Test
    fun `summarizeSelectedDaysOfWeek returns abbreviated days for custom set`() {
        val days = setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)
        val summary = TimeUtils.summarizeSelectedDaysOfWeek(days)
        assertThat(summary).isEqualTo("Mon, Wed, Fri")
    }
}