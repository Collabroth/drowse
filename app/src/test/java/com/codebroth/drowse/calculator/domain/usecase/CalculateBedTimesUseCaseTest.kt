package com.codebroth.drowse.calculator.domain.usecase

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.time.LocalTime

class CalculateBedTimesUseCaseTest {

    private val useCase = CalculateBedTimesUseCase()

    @Test
    fun `getBedTimesUseCase returns correct bed times for 6 cycles`() {
        val wakeUpTime = LocalTime.of(9, 0)
        val sleepCycleDurationMinutes = 90
        val fallAsleepBufferMinutes = 15
        val result = useCase(
            wakeUpTime,
            sleepCycleDurationMinutes,
            fallAsleepBufferMinutes
        )

        assertThat(result).hasSize(6)

        for (i in 0 until 6) {
            val cycles = 6 - i
            val expectedTotalMinutes = cycles * sleepCycleDurationMinutes + fallAsleepBufferMinutes
            val expectedTime = wakeUpTime.minusMinutes(expectedTotalMinutes.toLong())
            val recommendation = result[i]

            assertThat(recommendation.cycles).isEqualTo(cycles)
            assertThat(recommendation.minutes).isEqualTo(expectedTotalMinutes)
            assertThat(recommendation.time).isEqualTo(expectedTime)
        }
    }
}