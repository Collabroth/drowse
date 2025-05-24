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
    val is24HourFormat: Boolean
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
                is24HourFormat = preferences[IS_24_HOUR_FORMAT] == true
            )
        }

    suspend fun set24HourFormat(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_24_HOUR_FORMAT] = enabled
        }
    }

    private companion object {
        val IS_24_HOUR_FORMAT = booleanPreferencesKey("is_24_hour_format")
        const val TAG = "UserPreferencesRepo"
    }
}