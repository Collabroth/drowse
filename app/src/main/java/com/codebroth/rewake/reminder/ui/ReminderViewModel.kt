package com.codebroth.rewake.reminder.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codebroth.rewake.reminder.domain.model.Reminder
import com.codebroth.rewake.reminder.domain.usecase.AddReminderUseCase
import com.codebroth.rewake.reminder.domain.usecase.DeleteReminderUseCase
import com.codebroth.rewake.reminder.domain.usecase.GetAllRemindersUseCase
import com.codebroth.rewake.reminder.domain.usecase.UpdateReminderUseCase
import com.codebroth.rewake.reminder.notifications.ReminderAlarmScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
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

    var uiState by mutableStateOf(ReminderUiState())
        private set

    fun showDialog(editing: Reminder? = null) {
        uiState = uiState.copy(
            isDialogOpen = true,
            editingReminder = editing
        )
    }

    fun dismissDialog() {
        uiState = uiState.copy(
            isDialogOpen = false,
            editingReminder = null
        )
    }

    fun onSaveReminder(reminder: Reminder) {
        viewModelScope.launch {
            if (reminder.id.toInt() == 0) {
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