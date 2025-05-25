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

package com.codebroth.rewake.core.data.local

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

data class UserPreferences(
    val is24HourFormat: Boolean = false,
    val useAlarmClockApi: Boolean = false,
)

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            UserPreferences(
                is24HourFormat = preferences[IS_24_HOUR_FORMAT] == true,
                useAlarmClockApi = preferences[USE_ALARM_CLOCK_API] == true,
            )
        }

    suspend fun set24HourFormat(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_24_HOUR_FORMAT] = enabled
        }
    }

    suspend fun setUseAlarmClockApi(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[USE_ALARM_CLOCK_API] = enabled
        }
    }

    private companion object {
        val IS_24_HOUR_FORMAT = booleanPreferencesKey("is_24_hour_format")
        val USE_ALARM_CLOCK_API = booleanPreferencesKey("use_alarm_clock_api")

        const val TAG = "UserPreferencesRepo"
    }
}