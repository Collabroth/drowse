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

package com.codebroth.rewake.reminder.domain.model

import com.codebroth.rewake.core.domain.util.toBitmask
import com.codebroth.rewake.reminder.data.local.ReminderEntity
import java.time.DayOfWeek

data class Reminder(
    val id: Int = 0,
    val hour: Int,
    val minute: Int,
    val daysOfWeek: Set<DayOfWeek>,
    val label: String? = null
)

fun Reminder.fromDomainModel(): ReminderEntity =
    ReminderEntity(
        id = id,
        hour = hour,
        minute = minute,
        daysOfWeekBitmask = daysOfWeek.toBitmask(),
        label = label
    )
