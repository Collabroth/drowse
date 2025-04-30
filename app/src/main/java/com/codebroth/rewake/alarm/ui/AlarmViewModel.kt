package com.codebroth.rewake.alarm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codebroth.rewake.alarm.domain.model.Alarm
import com.codebroth.rewake.alarm.domain.usecase.AddAlarmUseCase
import com.codebroth.rewake.alarm.domain.usecase.DeleteAlarmUseCase
import com.codebroth.rewake.alarm.domain.usecase.GetAllAlarmUseCase
import com.codebroth.rewake.alarm.domain.usecase.UpdateAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
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
}