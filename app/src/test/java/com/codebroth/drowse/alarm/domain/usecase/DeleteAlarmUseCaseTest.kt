package com.codebroth.drowse.alarm.domain.usecase

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteAlarmUseCaseTest : BaseAlarmTest() {

    private lateinit var deleteAlarm: DeleteAlarmUseCase

    @Before
    override fun setUp() {
        super.setUp()
        insertDefaultAlarms()

        deleteAlarm = DeleteAlarmUseCase(fakeAlarmRepository)
    }

    @Test
    fun `deleteAlarm removes an existing alarm from the repository`() = runBlocking {
        val alarmIdToDelete = 5 // already has this alarm in the list.

        val alarm = fakeAlarmRepository.getAlarmById(alarmIdToDelete).first()

        if (alarm != null) {
            deleteAlarm(alarm)
        }

        val alarms = fakeAlarmRepository.getAllAlarms()

        assertThat(alarms.first()).doesNotContain(alarm)
    }
}