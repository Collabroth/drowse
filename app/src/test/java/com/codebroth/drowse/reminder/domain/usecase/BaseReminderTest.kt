package com.codebroth.drowse.reminder.domain.usecase

import com.codebroth.drowse.reminder.data.repository.FakeReminderRepository
import com.codebroth.drowse.reminder.domain.model.Reminder
import kotlinx.coroutines.runBlocking
import org.junit.Before
import java.time.DayOfWeek

abstract class BaseReminderTest {
    protected lateinit var fakeReminderRepository: FakeReminderRepository

    @Before
    open fun setUp() {
        fakeReminderRepository = FakeReminderRepository()
    }

    /**
     * Inserts default reminders into the fake repository.
     */
    internal fun insertDefaultReminders() {
        val remindersToInsert = mutableListOf<Reminder>()
        (0..23).forEachIndexed { index, hour ->
            remindersToInsert.add(
                Reminder(
                    id = index,
                    hour = hour,
                    minute = 0,
                    daysOfWeek = DayOfWeek.entries.toSet(),
                    label = "Reminder $index",
                )
            )
        }
        runBlocking { remindersToInsert.forEach { fakeReminderRepository.insertReminder(it) } }
    }
}