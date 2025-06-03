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

package com.codebroth.drowse.core.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class AppDestination() {
    @Serializable
    object CalculatorDestination : AppDestination()

    @Serializable
    data class ReminderDestination(
        val setReminderHour: Int? = null,
        val setReminderMinutes: Int? = null
    ) : AppDestination()

    @Serializable
    data class AlarmDestination(
        val setAlarmHour: Int? = null,
        val setAlarmMinutes: Int? = null
    ) : AppDestination()

    @Serializable
    object SettingsDestination : AppDestination()
}

