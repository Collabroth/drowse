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

package com.codebroth.drowse.core.data.local

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import com.codebroth.drowse.core.domain.model.ThemePreference
import com.codebroth.drowse.core.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/**
 * Repository for managing user preferences using DataStore.
 */
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
                fallAsleepBuffer = preferences[FALL_ASLEEP_BUFFER] ?: 15,
                sleepCycleLengthMinutes = preferences[SLEEP_CYCLE_LENGTH_MINUTES] ?: 90,
                themePreference = preferences[THEME_PREFERENCE] ?: ThemePreference.AUTO.value,
                useDynamicColor = preferences[USE_DYNAMIC_COLOR] == true
            )
        }

    /**
     * Sets the user's preferred theme preference.
     * @param themePreference The user's preferred theme.
     * 0 for system default, 1 for light theme, 2 for dark theme.
     */
    suspend fun setThemePreference(themePreference: Int) {
        dataStore.edit { preferences ->
            preferences[THEME_PREFERENCE] = themePreference
        }
    }

    /**
     * Sets whether the user wants to use dynamic color.
     */
    suspend fun setUseDynamicColor(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[USE_DYNAMIC_COLOR] = enabled
        }
    }

    suspend fun set24HourFormat(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_24_HOUR_FORMAT] = enabled
        }
    }

    suspend fun setFallAsleepBuffer(minutes: Int) {
        dataStore.edit { preferences ->
            preferences[FALL_ASLEEP_BUFFER] = minutes
        }
    }

    suspend fun setSleepCycleLength(minutes: Int) {
        dataStore.edit { preferences ->
            preferences[SLEEP_CYCLE_LENGTH_MINUTES] = minutes
        }
    }

    private companion object {
        val THEME_PREFERENCE = intPreferencesKey("theme_preference")
        val USE_DYNAMIC_COLOR = booleanPreferencesKey("use_dynamic_color")
        val IS_24_HOUR_FORMAT = booleanPreferencesKey("is_24_hour_format")
        val FALL_ASLEEP_BUFFER = intPreferencesKey("fall_asleep_buffer")
        val SLEEP_CYCLE_LENGTH_MINUTES = intPreferencesKey("sleep_cycle_length_minutes")

        const val TAG = "UserPreferencesRepo"
    }
}