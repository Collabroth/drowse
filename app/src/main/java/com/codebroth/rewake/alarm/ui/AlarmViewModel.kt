package com.codebroth.rewake.alarm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codebroth.rewake.alarm.domain.model.Alarm
import com.codebroth.rewake.alarm.domain.usecase.AddAlarmUseCase
import com.codebroth.rewake.alarm.domain.usecase.DeleteAlarmUseCase
import com.codebroth.rewake.alarm.domain.usecase.GetAllAlarmUseCase
import com.codebroth.rewake.alarm.domain.usecase.UpdateAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    getAllAlarms: GetAllAlarmUseCase,
    private val addAlarm: AddAlarmUseCase,
    private val updateAlarm: UpdateAlarmUseCase,
    private val deleteAlarm: DeleteAlarmUseCase,
) : ViewModel() {
    val alarms: StateFlow<List<Alarm>> =
        getAllAlarms()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _alarmUiState = MutableStateFlow(AlarmUiState())
    val alarmUiState: StateFlow<AlarmUiState> = _alarmUiState.asStateFlow()


    fun showDialog(editing: Alarm? = null) {
        _alarmUiState.update { currentState ->
            currentState.copy(
                isDialogOpen = true,
                editingAlarm = editing
            )
        }
    }

    fun dismissDialog() {
        _alarmUiState.update { currentState ->
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
            } else {
                updateAlarm(alarm)
            }
            dismissDialog()
        }
    }

    fun onDeleteAlarm(alarm: Alarm) {
        viewModelScope.launch {
            deleteAlarm(alarm)
            dismissDialog()
        }
    }

    fun onToggleAlarm(alarm: Alarm, enabled: Boolean) {
        viewModelScope.launch {
            updateAlarm(alarm.copy(isEnabled = enabled))
        }
    }

    data class AlarmUiState(
        val isDialogOpen: Boolean = false,
        val editingAlarm: Alarm? = null
    )
}