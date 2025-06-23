package com.codebroth.drowse.core.domain.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.time.DayOfWeek
import java.time.ZoneId
import java.time.ZonedDateTime

class TriggerTimeCalculatorTest {
    private val zone = ZoneId.of("UTC")

    @Test
    fun `nextTrigger returns next trigger instance if time is in the future and daysOfWeek is empty`() {
        val now = ZonedDateTime.of(2024, 6, 1, 8, 0, 0, 0, zone)
        val trigger = TriggerTimeCalculator.nextTrigger(
            daysOfWeek = emptySet(),
            hour = 9,
            minute = 0,
            now = now
        )
        assertThat(trigger.dayOfMonth).isEqualTo(1)
        assertThat(trigger.hour).isEqualTo(9)
        assertThat(trigger.minute).isEqualTo(0)
    }

    @Test
    fun `nextTrigger returns next trigger instance if time is in the past and daysOfWeek is empty`() {
        val now = ZonedDateTime.of(2024, 6, 1, 10, 0, 0, 0, zone)
        val trigger = TriggerTimeCalculator.nextTrigger(
            daysOfWeek = emptySet(),
            hour = 9,
            minute = 0,
            now = now
        )
        assertThat(trigger.dayOfMonth).isEqualTo(2)
        assertThat(trigger.hour).isEqualTo(9)
        assertThat(trigger.minute).isEqualTo(0)
    }

    @Test
    fun `nextTrigger returns next occurrence of specified day in daysOfWeek`() {
        val now = ZonedDateTime.of(2024, 6, 1, 8, 0, 0, 0, zone) // Saturday
        val trigger = TriggerTimeCalculator.nextTrigger(
            daysOfWeek = setOf(DayOfWeek.MONDAY),
            hour = 7,
            minute = 30,
            now = now
        )
        assertThat(trigger.dayOfWeek).isEqualTo(DayOfWeek.MONDAY)
        assertThat(trigger.hour).isEqualTo(7)
        assertThat(trigger.minute).isEqualTo(30)
        assertThat(trigger.dayOfMonth).isEqualTo(3) // Monday, June 3, 2024
    }

    @Test
    fun `nextTrigger returns next week if time for today has already passed`() {
        val now = ZonedDateTime.of(2024, 6, 3, 8, 0, 0, 0, zone) // Monday
        val trigger = TriggerTimeCalculator.nextTrigger(
            daysOfWeek = setOf(DayOfWeek.MONDAY),
            hour = 7,
            minute = 0,
            now = now
        )
        assertThat(trigger.dayOfWeek).isEqualTo(DayOfWeek.MONDAY)
        assertThat(trigger.hour).isEqualTo(7)
        assertThat(trigger.minute).isEqualTo(0)
        assertThat(trigger.dayOfMonth).isEqualTo(10) // Next Monday
    }

    @Test
    fun `nextTrigger returns soonest day in daysOfWeek`() {
        val now = ZonedDateTime.of(2024, 6, 1, 8, 0, 0, 0, zone) // Saturday
        val trigger = TriggerTimeCalculator.nextTrigger(
            daysOfWeek = setOf(DayOfWeek.SUNDAY, DayOfWeek.WEDNESDAY),
            hour = 6,
            minute = 0,
            now = now
        )
        assertThat(trigger.dayOfWeek).isEqualTo(DayOfWeek.SUNDAY)
        assertThat(trigger.dayOfMonth).isEqualTo(2)
        assertThat(trigger.hour).isEqualTo(6)
        assertThat(trigger.minute).isEqualTo(0)
    }
}