package com.codebroth.drowse.alarm.domain.usecase

import com.codebroth.drowse.alarm.data.repository.FakeAlarmRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetAllAlarmsUseCaseTest : BaseAlarmTest() {

    private lateinit var getAlarms: GetAllAlarmsUseCase

    @Before
    override fun setUp() {
        super.setUp()
        insertDefaultAlarms()
        getAlarms = GetAllAlarmsUseCase(fakeAlarmRepository)
    }

    @Test
    fun `getAllAlarms returns all alarms when list not empty`() = runBlocking {
        val alarms = getAlarms().first()

        assertThat(alarms).isNotEmpty()
        assertThat(alarms.size).isEqualTo(24)
    }

    @Test
    fun `getAllAlarms returns empty list when list empty`() = runBlocking {
        val emptyRepository = FakeAlarmRepository()
        val useCase = GetAllAlarmsUseCase(emptyRepository)
        val alarms = useCase().first()

        assertThat(alarms).isEmpty()
    }
}