package com.codebroth.drowse.reminder.domain.usecase

import com.codebroth.drowse.reminder.data.repository.FakeReminderRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetAllRemindersUseCaseTest : BaseReminderTest() {

    private lateinit var getReminders: GetAllRemindersUseCase

    @Before
    override fun setUp() {
        super.setUp()
        insertDefaultReminders()
        getReminders = GetAllRemindersUseCase(fakeReminderRepository)
    }

    @Test
    fun `getAllReminders returns all reminders when list not empty`() = runBlocking {
        val reminders = getReminders().first()

        assertNotNull(reminders)
        assertTrue(reminders.isNotEmpty())
        assertEquals(24, reminders.size)
    }

    @Test
    fun `getAllReminders returns empty list when list empty`() = runBlocking {
        val emptyRepository = FakeReminderRepository()
        val useCase = GetAllRemindersUseCase(emptyRepository)
        val reminders = useCase().first()

        assertNotNull(reminders)
        assertTrue(reminders.isEmpty())
    }
}