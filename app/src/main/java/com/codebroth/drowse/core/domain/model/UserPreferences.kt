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

package com.codebroth.drowse.core.domain.model

/**
 * Data class representing user preferences.
 *
 * @property is24HourFormat Whether to use 24-hour time format.
 * @property useAlarmClockApi Whether to use the Alarm Clock API.
 * @property fallAsleepBuffer The buffer time in minutes before sleep.
 * @property sleepCycleLengthMinutes The length of a sleep cycle in minutes.
 */
data class UserPreferences(
    val themePreference: Int,
    val is24HourFormat: Boolean,
    val useAlarmClockApi: Boolean,
    val fallAsleepBuffer: Int,
    val sleepCycleLengthMinutes: Int,
) {
    companion object {
        val DEFAULT = UserPreferences(
            themePreference = 0,
            is24HourFormat = false,
            useAlarmClockApi = true,
            fallAsleepBuffer = 15,
            sleepCycleLengthMinutes = 90,
        )
    }
}
