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

package com.codebroth.drowse.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codebroth.drowse.alarm.data.scheduling.AlarmSchedulerService
import com.codebroth.drowse.core.data.local.UserPreferencesRepository
import com.codebroth.drowse.settings.data.EmailSender
import com.codebroth.drowse.settings.data.UriIntentLauncher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing settings in the application.
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val alarmSchedulerService: AlarmSchedulerService,
    private val feedbackEmailSender: EmailSender,
    private val uriIntentLauncher: UriIntentLauncher,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> =
        combine(
            _uiState,
            userPreferencesRepository.userPreferencesFlow
        ) { state, userPreferences ->
            state.copy(
                is24HourFormat = userPreferences.is24HourFormat,
                useAlarmClockApi = userPreferences.useAlarmClockApi,
                fallAsleepBuffer = userPreferences.fallAsleepBuffer,
                sleepCycleLength = userPreferences.sleepCycleLengthMinutes
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = _uiState.value
            )

    fun onToggle24HourFormat(is24HourFormat: Boolean) = viewModelScope.launch {
        userPreferencesRepository.set24HourFormat(is24HourFormat)
    }

    fun onToggleUseAlarmClockApi(useAlarmClockApi: Boolean) = viewModelScope.launch {
        userPreferencesRepository.setUseAlarmClockApi(useAlarmClockApi)
        alarmSchedulerService.cancelAllActive()
    }

    fun onFallAsleepBufferChanged(minutes: Int) = viewModelScope.launch {
        userPreferencesRepository.setFallAsleepBuffer(minutes)
    }

    fun onSleepCycleLengthChanged(minutes: Int) = viewModelScope.launch {
        userPreferencesRepository.setSleepCycleLength(minutes)
    }

    fun onClickSendFeedback() {
        feedbackEmailSender.send()
    }

    fun onClickGithub() {
        uriIntentLauncher.openUrl("https://github.com/Collabroth/drowse")
    }
}

data class SettingsUiState(
    val is24HourFormat: Boolean = false,
    val useAlarmClockApi: Boolean = false,
    val fallAsleepBuffer: Int = 15,
    val sleepCycleLength: Int = 90,
)