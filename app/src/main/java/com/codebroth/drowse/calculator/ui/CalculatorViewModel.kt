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

package com.codebroth.drowse.calculator.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codebroth.drowse.calculator.data.AlarmClockIntentLauncher
import com.codebroth.drowse.calculator.domain.model.SleepRecommendation
import com.codebroth.drowse.calculator.domain.usecase.CalculateBedTimesUseCase
import com.codebroth.drowse.calculator.domain.usecase.CalculateWakeTimesUseCase
import com.codebroth.drowse.calculator.ui.component.CalculatorMode
import com.codebroth.drowse.core.data.local.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime

/**
 * ViewModel for the sleep calculator screen, managing the state and interactions.
 *
 * @property preferencesRepository Repository for user preferences.
 * @property wakeTimesUseCase Use case for calculating wake times.
 * @property bedTimesUseCase Use case for calculating bed times.
 */
@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val preferencesRepository: UserPreferencesRepository,
    private val wakeTimesUseCase: CalculateWakeTimesUseCase,
    private val bedTimesUseCase: CalculateBedTimesUseCase,
) : ViewModel() {

    @Inject
    lateinit var alarmClockLauncher: AlarmClockIntentLauncher

    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            preferencesRepository.userPreferencesFlow.collect { preferences ->
                _uiState.update { currentState ->
                    currentState.copy(
                        is24HourFormat = preferences.is24HourFormat,
                        fallAsleepBuffer = preferences.fallAsleepBuffer,
                        sleepCycleLength = preferences.sleepCycleLengthMinutes
                    )
                }
            }
        }
    }

    fun onModeChange(mode: CalculatorMode) {
        _uiState.update {
            it.copy(
                mode = mode,
                selectedTime = getDefaultTimeForMode(mode),
                recommendations = emptyList()
            )
        }
    }

    fun onShowTimePicker(show: Boolean) {
        _uiState.update { it.copy(showTimePicker = show) }
    }

    fun onTimeSelected(time: LocalTime) {
        _uiState.update { it.copy(selectedTime = time, showTimePicker = false) }
        onCalculate()
    }

    fun onClickSleepNow() {
        _uiState.update { it.copy(selectedTime = LocalTime.now()) }
        onCalculate()
    }

    fun onCalculate() {
        val state = _uiState.value
        val recs = when (state.mode) {
            CalculatorMode.WAKE_UP_TIME -> bedTimesUseCase(
                wakeUpTime = state.selectedTime,
                sleepCycleDurationMinutes = state.sleepCycleLength,
                fallAsleepBufferMinutes = state.fallAsleepBuffer
            )

            CalculatorMode.BED_TIME -> wakeTimesUseCase(
                bedtime = state.selectedTime,
                sleepCycleDurationMinutes = state.sleepCycleLength,
                fallAsleepBufferMinutes = state.fallAsleepBuffer,

                )
        }
        _uiState.update { it.copy(recommendations = recs) }
    }

    fun sendAlarmClockIntent(time: LocalTime) {
        alarmClockLauncher.startAlarmSetAction(time.hour, time.minute)
    }

    private fun getDefaultTimeForMode(mode: CalculatorMode): LocalTime {
        return when (mode) {
            CalculatorMode.WAKE_UP_TIME -> LocalTime.of(9, 0)
            CalculatorMode.BED_TIME -> LocalTime.of(23, 0)
        }
    }

    /**
     * Data class representing the UI state for the calculator screen.
     */
    data class CalculatorUiState(
        val mode: CalculatorMode = CalculatorMode.WAKE_UP_TIME,
        val selectedTime: LocalTime = if (mode == CalculatorMode.WAKE_UP_TIME) {
            LocalTime.of(9, 0)
        } else {
            LocalTime.of(23, 0)
        },
        val recommendations: List<SleepRecommendation> = emptyList(),
        val showTimePicker: Boolean = false,

        // user preferences
        val is24HourFormat: Boolean = false,
        val fallAsleepBuffer: Int = 15,
        val sleepCycleLength: Int = 90
    )
}