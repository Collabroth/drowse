package com.codebroth.drowse.alarm.domain.usecase

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetAlarmByIdUseCaseTest : BaseAlarmTest() {

    private lateinit var getAlarm: GetAlarmByIdUseCase

    @Before
    override fun setUp() {
        super.setUp()
        insertDefaultAlarms()
        getAlarm = GetAlarmByIdUseCase(fakeAlarmRepository)
    }

    @Test
    fun `getAlarmById returns correct alarm when it exists`() = runBlocking {
        val alarmId = 5
        val alarm = getAlarm(alarmId).first()

        assertThat(alarm).isNotNull()
        assertThat(alarm?.id).isEqualTo(alarmId)
    }
}