package com.codebroth.rewake.core.data.scheduling

import java.time.DayOfWeek
import java.time.ZonedDateTime
import java.time.temporal.TemporalAdjusters

object TriggerTimeCalculator {
    fun nextTrigger(
        daysOfWeek: Set<DayOfWeek>,
        hour: Int,
        minute: Int,
        now: ZonedDateTime = ZonedDateTime.now()
    ): ZonedDateTime {
        if (daysOfWeek.isEmpty()) {
            val candidate = now
                .withHour(hour)
                .withMinute(minute)
                .withSecond(0)
            return if (candidate.isAfter(now)) {
                candidate
            } else {
                candidate.plusDays(1)
            }
        }
        return daysOfWeek.minOf { dow ->
            var candidate = now
                .with(TemporalAdjusters.nextOrSame(dow))
                .withHour(hour)
                .withMinute(minute)
                .withSecond(0)
            if (candidate.isBefore(now)) {
                candidate = candidate.plusWeeks(1)
            }
            candidate
        }
    }
}