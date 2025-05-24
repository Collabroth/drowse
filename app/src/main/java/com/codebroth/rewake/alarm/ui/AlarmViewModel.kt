package com.codebroth.rewake.alarm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codebroth.rewake.alarm.data.scheduling.AlarmSchedulerService
import com.codebroth.rewake.alarm.domain.model.Alarm
import com.codebroth.rewake.alarm.domain.usecase.AddAlarmUseCase
import com.codebroth.rewake.alarm.domain.usecase.DeleteAlarmUseCase
import com.codebroth.rewake.alarm.domain.usecase.GetAllAlarmsUseCase
import com.codebroth.rewake.alarm.domain.usecase.UpdateAlarmUseCase
import com.codebroth.rewake.core.data.local.UserPreferencesRepository
import com.codebroth.rewake.core.ui.component.snackbar.SnackBarEvent
import com.codebroth.rewake.core.ui.component.snackbar.SnackbarController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    getAllAlarms: GetAllAlarmsUseCase,
    private val addAlarm: AddAlarmUseCase,
    private val updateAlarm: UpdateAlarmUseCase,
    private val deleteAlarm: DeleteAlarmUseCase,
    private val schedulerService: AlarmSchedulerService,
    private val preferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AlarmUiState())
    val uiState: StateFlow<AlarmUiState> =
        combine(
            _uiState,
            preferencesRepository.userPreferencesFlow
        ) { state, userPreferences ->
            state.copy(is24HourFormat = userPreferences.is24HourFormat)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = _uiState.value.copy(is24HourFormat = false)
            )

    val alarms: StateFlow<List<Alarm>> =
        getAllAlarms()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun showDialog(editing: Alarm? = null) {
        _uiState.update { currentState ->
            currentState.copy(
                isDialogOpen = true,
                editingAlarm = editing
            )
        }
    }

    fun dismissDialog() {
        _uiState.update { currentState ->
            currentState.copy(
                isDialogOpen = false,
                editingAlarm = null
            )
        }
    }

    fun onSaveAlarm(alarm: Alarm) {
        viewModelScope.launch {
            if (alarm.id == 0) {
                val newId = addAlarm(alarm)
                schedulerService.scheduleNext(alarm, newId)
            } else {
                updateAlarm(alarm)
                schedulerService.reschedule(alarm, alarm.id)
            }
            showSnackbar(computeOnAlarmSetMessage(alarm))
            dismissDialog()
        }
    }

    fun onDeleteAlarm(alarm: Alarm) {
        viewModelScope.launch {
            deleteAlarm(alarm)
            schedulerService.cancel(alarm.id)
            dismissDialog()
        }
    }

    fun onToggleAlarm(alarm: Alarm, enabled: Boolean) {
        viewModelScope.launch {
            updateAlarm(alarm.copy(isEnabled = enabled))
            if (enabled) {
                schedulerService.scheduleNext(alarm, alarm.id)
            } else {
                schedulerService.cancel(alarm.id)
            }
        }
    }

    private fun showSnackbar(message: String) {
        viewModelScope.launch {
            SnackbarController.sendEvent(
                event = SnackBarEvent(
                    message = message
                )
            )
        }
    }

    private fun computeOnAlarmSetMessage(alarm: Alarm): String {
        val now = LocalDateTime.now()
        val targetTime = LocalTime.of(alarm.hour, alarm.minute)
        val days = alarm.daysOfWeek

        val nextDate: LocalDate = if (days.isEmpty()) {
            val today = now.toLocalDate()
            if (now.toLocalTime() >= targetTime) today.plusDays(1) else today
        } else {
            days.minOf { dow ->
                var d = now.toLocalDate()
                    .with(TemporalAdjusters.nextOrSame(dow))
                if (d == now.toLocalDate() && now.toLocalTime() >= targetTime) {
                    d = d.plusWeeks(1)
                }
                d
            }
        }

        val nextDateTime = LocalDateTime.of(nextDate, targetTime)

        val duration = Duration.between(now, nextDateTime)
        val hours = duration.toHours()
        val minutes = duration.toMinutes() % 60

        return "Reminder set for ${hours}h and ${minutes}m from now"
    }

    data class AlarmUiState(
        val isDialogOpen: Boolean = false,
        val editingAlarm: Alarm? = null,
        val is24HourFormat: Boolean = false,
    )
}