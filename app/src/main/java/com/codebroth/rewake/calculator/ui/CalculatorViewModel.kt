package com.codebroth.rewake.calculator.ui

import androidx.lifecycle.ViewModel
import com.codebroth.rewake.calculator.domain.model.SleepRecommendation
import com.codebroth.rewake.calculator.domain.usecase.CalculateBedTimesUseCase
import com.codebroth.rewake.calculator.domain.usecase.CalculateWakeTimesUseCase
import com.codebroth.rewake.calculator.ui.component.CalculatorMode
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalTime

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val wakeTimesUseCase: CalculateWakeTimesUseCase,
    private val bedTimesUseCase: CalculateBedTimesUseCase
) : ViewModel() {

    private val _calculatorUiState = MutableStateFlow(CalculatorUiState())
    val calculatorUiState: StateFlow<CalculatorUiState> = _calculatorUiState.asStateFlow()

    fun onModeChange(mode: CalculatorMode) {
        _calculatorUiState.update { it.copy(mode = mode, recommendations = emptyList()) }
    }

    fun onShowTimePicker(show: Boolean) {
        _calculatorUiState.update { it.copy(showTimePicker = show) }
    }

    fun onTimeSelected(time: LocalTime) {
        _calculatorUiState.update { it.copy(selectedTime = time, showTimePicker = false) }
        onCalculate()
    }

    fun onCalculate() {
        val st = _calculatorUiState.value
        val recs = when (st.mode) {
            CalculatorMode.WAKE_UP_TIME -> bedTimesUseCase(st.selectedTime)
            CalculatorMode.BED_TIME -> wakeTimesUseCase(st.selectedTime)
        }
        _calculatorUiState.update { it.copy(recommendations = recs) }
    }

    data class CalculatorUiState(
        val mode: CalculatorMode = CalculatorMode.WAKE_UP_TIME,
        val selectedTime: LocalTime = LocalTime.of(7, 30),
        val recommendations: List<SleepRecommendation> = emptyList(),
        val showTimePicker: Boolean = false
    )
}