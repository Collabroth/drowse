package com.codebroth.rewake.reminder.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codebroth.rewake.reminder.domain.model.Reminder
import com.codebroth.rewake.reminder.domain.usecase.AddReminderUseCase
import com.codebroth.rewake.reminder.domain.usecase.DeleteReminderUseCase
import com.codebroth.rewake.reminder.domain.usecase.GetAllRemindersUseCase
import com.codebroth.rewake.reminder.domain.usecase.UpdateReminderUseCase
import com.codebroth.rewake.reminder.data.notifications.ReminderAlarmScheduler
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
class ReminderViewModel @Inject constructor(
    getAllReminders: GetAllRemindersUseCase,
    private val addReminder: AddReminderUseCase,
    private val updateReminder: UpdateReminderUseCase,
    private val deleteReminder: DeleteReminderUseCase,
    private val scheduler: ReminderAlarmScheduler
) : ViewModel() {
    val reminders: StateFlow<List<Reminder>> =
        getAllReminders()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    private val _reminderUiState = MutableStateFlow(ReminderUiState())
    val reminderUiState: StateFlow<ReminderUiState> = _reminderUiState.asStateFlow()

    fun showDialog(editing: Reminder? = null) {
        _reminderUiState.update { currentState ->
            currentState.copy(
                isDialogOpen = true,
                editingReminder = editing
            )
        }
    }

    fun dismissDialog() {
        _reminderUiState.update { currentState ->
            currentState.copy(
                isDialogOpen = false,
                editingReminder = null
            )
        }
    }

    fun onSaveReminder(reminder: Reminder) {
        viewModelScope.launch {
            if (reminder.id == 0) {
                val newId = addReminder(reminder)
                scheduler.scheduleReminder(newId, reminder)
            } else {
                updateReminder(reminder)
                scheduler.rescheduleReminder(reminder.id, reminder)
            }
            dismissDialog()
        }
    }

    fun onDeleteReminder(reminder: Reminder) {
        viewModelScope.launch {
            deleteReminder(reminder)
            scheduler.cancelReminder(reminder.id)
        }
    }

    data class ReminderUiState(
        val isDialogOpen: Boolean = false,
        val editingReminder: Reminder? = null
    )
}