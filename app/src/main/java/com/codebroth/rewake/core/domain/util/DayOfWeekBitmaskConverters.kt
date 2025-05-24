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


private const val MON_BIT = 1 shl 0
private const val TUE_BIT = 1 shl 1
private const val WED_BIT = 1 shl 2
private const val THU_BIT = 1 shl 3
private const val FRI_BIT = 1 shl 4
private const val SAT_BIT = 1 shl 5
private const val SUN_BIT = 1 shl 6

internal fun Set<DayOfWeek>.toBitmask(): Int =
    this.fold(0) { mask, day ->
        mask or when (day) {
            DayOfWeek.MONDAY -> MON_BIT
            DayOfWeek.TUESDAY -> TUE_BIT
            DayOfWeek.WEDNESDAY -> WED_BIT
            DayOfWeek.THURSDAY -> THU_BIT
            DayOfWeek.FRIDAY -> FRI_BIT
            DayOfWeek.SATURDAY -> SAT_BIT
            DayOfWeek.SUNDAY -> SUN_BIT
        }
    }

internal fun Int.toDayOfWeekSet(): Set<DayOfWeek> =
    DayOfWeek.entries.toTypedArray().filterIndexed { idx, _ ->
        (this and (1 shl idx)) != 0
    }.toSet()