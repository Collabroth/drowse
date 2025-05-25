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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.time.LocalTime

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val wakeTimesUseCase: CalculateWakeTimesUseCase,
    private val bedTimesUseCase: CalculateBedTimesUseCase,
    private val preferencesRepository: UserPreferencesRepository
) : ViewModel() {

    @Inject
    lateinit var alarmClockLauncher: AlarmClockIntentLauncher

    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState: StateFlow<CalculatorUiState> =
        combine(
            _uiState,
            preferencesRepository.userPreferencesFlow
        ) { state, userPreferences ->
            state.copy(
                is24HourFormat = userPreferences.is24HourFormat,
                useAlarmClockApi = userPreferences.useAlarmClockApi,
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = _uiState.value.copy(
                    is24HourFormat = false,
                    useAlarmClockApi = false,
                )
            )

    fun onModeChange(mode: CalculatorMode) {
        _uiState.update { it.copy(mode = mode, recommendations = emptyList()) }
    }

    fun onShowTimePicker(show: Boolean) {
        _uiState.update { it.copy(showTimePicker = show) }
    }

    fun onTimeSelected(time: LocalTime) {
        _uiState.update { it.copy(selectedTime = time, showTimePicker = false) }
        onCalculate()
    }

    fun onCalculate() {
        val st = _uiState.value
        val recs = when (st.mode) {
            CalculatorMode.WAKE_UP_TIME -> bedTimesUseCase(st.selectedTime)
            CalculatorMode.BED_TIME -> wakeTimesUseCase(st.selectedTime)
        }
        _uiState.update { it.copy(recommendations = recs) }
    }

    fun sendAlarmClockIntent(time: LocalTime) {
        alarmClockLauncher.startAlarmSetAction(time.hour, time.minute)
    }

    data class CalculatorUiState(
        val mode: CalculatorMode = CalculatorMode.WAKE_UP_TIME,
        val selectedTime: LocalTime = LocalTime.of(7, 30),
        val recommendations: List<SleepRecommendation> = emptyList(),
        val showTimePicker: Boolean = false,

        // user preferences
        val is24HourFormat: Boolean = false,
        val useAlarmClockApi: Boolean = false,
    )
}