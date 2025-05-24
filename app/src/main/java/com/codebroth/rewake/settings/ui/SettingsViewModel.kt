package com.codebroth.rewake.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codebroth.rewake.core.data.local.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val uiState: StateFlow<SettingsUiState> =
        userPreferencesRepository.userPreferencesFlow
            .map { userPreferences ->
                SettingsUiState(is24HourFormat = userPreferences.is24HourFormat)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SettingsUiState()
            )

    fun onToggle24HourFormat(is24HourFormat: Boolean) = viewModelScope.launch {
        userPreferencesRepository.set24HourFormat(is24HourFormat)
    }
}

data class SettingsUiState(
    val is24HourFormat: Boolean = false,
)