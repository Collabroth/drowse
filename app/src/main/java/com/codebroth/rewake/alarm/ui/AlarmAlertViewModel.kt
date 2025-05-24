package com.codebroth.rewake.alarm.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codebroth.rewake.alarm.data.scheduling.AlarmAlertHandler
import com.codebroth.rewake.core.data.local.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel for the Alarm Alert screen.
 *
 * @param alarmHandler The handler for managing alarm alerts.
 * @param userPreferencesRepository The repository for user preferences.
 */
@HiltViewModel
class AlarmAlertViewModel @Inject constructor(
    private val alarmHandler: AlarmAlertHandler,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _shouldFinish = MutableLiveData<Boolean>(false)
    val shouldFinish: LiveData<Boolean> = _shouldFinish

    val is24HourFormat: StateFlow<Boolean> =
        userPreferencesRepository.userPreferencesFlow
            .map { it.is24HourFormat }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false
            )

    /**
     * Called when the alarm is dismissed.
     * This will dismiss the alarm and set the shouldFinish flag to true.
     */
    fun onDismiss() {
        alarmHandler.dismissAlarm()
        _shouldFinish.value = true
    }

    /**
     * Called when the snooze button is pressed.
     * This will snooze the alarm and set the shouldFinish flag to true.
     */
    fun onSnooze() {
        alarmHandler.snoozeAlarm()
        _shouldFinish.value = true
    }
}