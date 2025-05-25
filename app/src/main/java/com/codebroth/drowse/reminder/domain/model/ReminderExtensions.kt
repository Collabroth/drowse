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

package com.codebroth.drowse.reminder.domain.model

import com.codebroth.drowse.core.domain.util.TimeUtils
import java.time.LocalTime

fun Reminder.formattedTime(is24Hour: Boolean = false): String {
    val time = LocalTime.of(hour, minute)
    return TimeUtils.formatTime(time, is24Hour)
}