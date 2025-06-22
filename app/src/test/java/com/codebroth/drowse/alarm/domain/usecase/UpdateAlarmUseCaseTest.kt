package com.codebroth.drowse.alarm.domain.usecase

import com.codebroth.drowse.alarm.domain.model.Alarm
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.DayOfWeek

class UpdateAlarmUseCaseTest : BaseAlarmTest() {

    private lateinit var updateAlarm: UpdateAlarmUseCase

    @Before
    override fun setUp() {
        super.setUp()
        insertDefaultAlarms()

        updateAlarm = UpdateAlarmUseCase(fakeAlarmRepository)
    }

    @Test
    fun `updateAlarm updates an existing alarm in the repository`() = runBlocking {
        val existingAlarm = fakeAlarmRepository.getAlarmById(5).first()
        val expectedAlarm = Alarm(
            id = 5,
            hour = 5,
            minute = 45,
            isEnabled = false,
            daysOfWeek = DayOfWeek.entries.toSet(),
            label = "Updated Alarm"
        )

        if (existingAlarm != null) {
            val updatedAlarm = existingAlarm.copy(
                hour = expectedAlarm.hour,
                minute = expectedAlarm.minute,
                isEnabled = expectedAlarm.isEnabled,
                daysOfWeek = expectedAlarm.daysOfWeek,
                label = expectedAlarm.label
            )

            updateAlarm(updatedAlarm)
        }
        val retrievedAlarm = fakeAlarmRepository.getAlarmById(5).first()

        assertThat(retrievedAlarm).isEqualTo(expectedAlarm)
    }

}