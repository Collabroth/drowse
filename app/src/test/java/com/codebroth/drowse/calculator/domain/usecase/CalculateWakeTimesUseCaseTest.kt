package com.codebroth.drowse.calculator.domain.usecase

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.time.LocalTime

class CalculateWakeTimesUseCaseTest {

    private val useCase = CalculateWakeTimesUseCase()

    @Test
    fun `getWakeTimesUseCase returns correct wake times for 6 cycles`() {
        val bedTime = LocalTime.of(22, 0)
        val sleepCycleDurationMinutes = 90
        val fallAsleepBufferMinutes = 15
        val result = useCase(
            bedTime,
            sleepCycleDurationMinutes,
            fallAsleepBufferMinutes
        )

        assertThat(result).hasSize(6)

        for (i in 0 until 6) {
            val cycles = 6 - i
            val expectedTotalMinutes = cycles * sleepCycleDurationMinutes + fallAsleepBufferMinutes
            val expectedTime = bedTime.plusMinutes(expectedTotalMinutes.toLong())
            val recommendation = result[i]

            assertThat(recommendation.cycles).isEqualTo(cycles)
            assertThat(recommendation.minutes).isEqualTo(expectedTotalMinutes)
            assertThat(recommendation.time).isEqualTo(expectedTime)
        }
    }
}