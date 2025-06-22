package com.codebroth.drowse.reminder.domain.usecase

import com.codebroth.drowse.reminder.domain.model.Reminder
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.DayOfWeek

class UpdateReminderUseCaseTest : BaseReminderTest() {

    private lateinit var updateReminder: UpdateReminderUseCase

    private lateinit var reminderToUpdate: Reminder

    @Before
    override fun setUp() {
        super.setUp()
        insertDefaultReminders()
        updateReminder = UpdateReminderUseCase(fakeReminderRepository)

        val existingReminder = Reminder(
            id = 57,
            hour = 8,
            minute = 30,
            daysOfWeek = DayOfWeek.entries.toSet(),
            label = "Test Reminder"
        )

        runBlocking {
            fakeReminderRepository.insertReminder(existingReminder)
        }

        reminderToUpdate = existingReminder.copy(
            id = 57,
            hour = 6,
            minute = 45,
            daysOfWeek = DayOfWeek.entries.toSet(),
            label = "Updated Reminder"
        )
    }

    @Test
    fun `updateReminder updates an existing reminder in the repository`() = runBlocking {

        updateReminder(reminderToUpdate)
        val reminders = fakeReminderRepository.getAllReminders().first()

        assertThat(reminders).contains(reminderToUpdate)
    }

}