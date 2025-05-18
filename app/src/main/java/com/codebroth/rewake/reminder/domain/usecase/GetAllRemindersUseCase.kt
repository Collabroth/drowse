package com.codebroth.rewake.reminder.domain.usecase

import com.codebroth.rewake.reminder.data.repository.ReminderRepository
import com.codebroth.rewake.reminder.domain.model.Reminder
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllRemindersUseCase @Inject constructor(
    private val repo: ReminderRepository
) {
    operator fun invoke(): Flow<List<Reminder>> =
        repo.getAllReminders()
}