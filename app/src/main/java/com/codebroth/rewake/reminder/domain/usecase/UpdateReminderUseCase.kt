package com.codebroth.rewake.reminder.domain.usecase

import com.codebroth.rewake.reminder.data.repository.ReminderRepository
import com.codebroth.rewake.reminder.domain.model.Reminder
import javax.inject.Inject

class UpdateReminderUseCase @Inject constructor(
    private val repo: ReminderRepository,
) {
    suspend operator fun invoke(reminder: Reminder) {
        repo.updateReminder(reminder)
    }
}