package com.codebroth.drowse.core.domain.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.time.DayOfWeek

class DayOfWeekBitmaskConvertersKtTest {

    @Test
    fun `toBitmask with single day`() {
        val days = setOf(DayOfWeek.MONDAY)
        val expectedBitmask = 1 // Monday is bit 0
        assertThat(days.toBitmask()).isEqualTo(expectedBitmask)
    }

    @Test
    fun `toBitmask with multiple days`() {
        val days = setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)
        // Monday (bit 0) = 1
        // Wednesday (bit 2) = 4
        // Friday (bit 4) = 16
        val expectedBitmask = 1 or 4 or 16 // 21
        assertThat(days.toBitmask()).isEqualTo(expectedBitmask)
    }

    @Test
    fun `toBitmask with all days`() {
        val days = DayOfWeek.entries.toSet()
        val expectedBitmask = (1 shl 7) - 1 // 127 (all bits 0-6 set)
        assertThat(days.toBitmask()).isEqualTo(expectedBitmask)
    }

    @Test
    fun `toBitmask with empty set`() {
        val days = emptySet<DayOfWeek>()
        val expectedBitmask = 0
        assertThat(days.toBitmask()).isEqualTo(expectedBitmask)
    }

    @Test
    fun `toDaysOfWeekSet with single bit`() {
        val bitmask = 1 // Monday
        val expectedDays = setOf(DayOfWeek.MONDAY)
        assertThat(bitmask.toDayOfWeekSet()).isEqualTo(expectedDays)
    }

    @Test
    fun `toDaysOfWeekSet with multiple bits`() {
        val bitmask = 1 or 4 or 16 // Monday, Wednesday, Friday (21)
        val expectedDays = setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)
        assertThat(bitmask.toDayOfWeekSet()).isEqualTo(expectedDays)
    }

    @Test
    fun `toDaysOfWeekSet with all bits set`() {
        val bitmask = (1 shl 7) - 1 // 127
        val expectedDays = DayOfWeek.entries.toSet()
        assertThat(bitmask.toDayOfWeekSet()).isEqualTo(expectedDays)
    }

    @Test
    fun `toDaysOfWeekSet with zero bitmask`() {
        val bitmask = 0
        val expectedDays = emptySet<DayOfWeek>()
        assertThat(bitmask.toDayOfWeekSet()).isEqualTo(expectedDays)
    }

    @Test
    fun `toDaysOfWeekSet with bitmask larger than possible days`() {
        val bitmask = 255 // Includes bits beyond DayOfWeek range
        val expectedDays = DayOfWeek.entries.toSet() // Should only return valid DayOfWeek values
        assertThat(bitmask.toDayOfWeekSet()).isEqualTo(expectedDays)
    }

    @Test
    fun `toBitmask and toDaysOfWeekSet are inverses - single day`() {
        val initialDays = setOf(DayOfWeek.TUESDAY)
        val bitmask = initialDays.toBitmask()
        val finalDays = bitmask.toDayOfWeekSet()
        assertThat(finalDays).isEqualTo(initialDays)
    }

    @Test
    fun `toBitmask and toDaysOfWeekSet are inverses - multiple days`() {
        val initialDays = setOf(DayOfWeek.SUNDAY, DayOfWeek.SATURDAY, DayOfWeek.THURSDAY)
        val bitmask = initialDays.toBitmask()
        val finalDays = bitmask.toDayOfWeekSet()
        assertThat(finalDays).isEqualTo(initialDays)
    }

    @Test
    fun `toBitmask and toDaysOfWeekSet are inverses - empty set`() {
        val initialDays = emptySet<DayOfWeek>()
        val bitmask = initialDays.toBitmask()
        val finalDays = bitmask.toDayOfWeekSet()
        assertThat(finalDays).isEqualTo(initialDays)
    }
}