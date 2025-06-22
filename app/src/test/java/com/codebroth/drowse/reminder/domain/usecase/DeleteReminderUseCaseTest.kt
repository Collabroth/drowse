package com.codebroth.drowse.reminder.domain.usecase

import com.codebroth.drowse.reminder.domain.model.Reminder
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.DayOfWeek

class DeleteReminderUseCaseTest : BaseReminderTest() {

    private lateinit var deleteReminder: DeleteReminderUseCase

    private lateinit var reminderToDelete: Reminder

    @Before
    override fun setUp() {
        super.setUp()
        insertDefaultReminders()

        deleteReminder = DeleteReminderUseCase(fakeReminderRepository)

        reminderToDelete = Reminder(
            id = 56,
            hour = 8,
            minute = 30,
            daysOfWeek = setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
            label = "Test Reminder"
        )

        // Insert the reminder first so we can delete it later
        runBlocking {
            fakeReminderRepository.insertReminder(reminderToDelete)
        }
    }

    @Test
    fun `deleteReminder removes an existing reminder from the repository`() = runBlocking {

        deleteReminder(reminderToDelete)

        val reminders = fakeReminderRepository.getAllReminders().first()

        assertThat(reminders).doesNotContain(reminderToDelete)
    }
}