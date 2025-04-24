package com.codebroth.rewake.reminder.domain.usecase

import com.codebroth.rewake.reminder.data.ReminderRepository
import com.codebroth.rewake.reminder.domain.model.Reminder
import javax.inject.Inject

class AddReminderUseCase @Inject constructor(
    private val repo: ReminderRepository
) {
    suspend operator fun invoke(reminder: Reminder): Long =
        repo.insertReminder(reminder)
}