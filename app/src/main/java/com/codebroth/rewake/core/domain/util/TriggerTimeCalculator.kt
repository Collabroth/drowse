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

package com.codebroth.rewake.core.domain.util

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