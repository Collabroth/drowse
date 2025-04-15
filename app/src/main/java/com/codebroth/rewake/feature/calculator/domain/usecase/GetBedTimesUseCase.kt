package com.codebroth.rewake.feature.calculator.domain.usecase

import com.codebroth.rewake.feature.calculator.domain.model.SleepRecommendation
import java.time.LocalTime

/**
 * Use case to calculate recommended bed times based on sleep cycles.
 *
 * @property sleepCycleDurationMinutes Duration of a single sleep cycle in minutes.
 * @property fallAsleepBufferMinutes Buffer time to fall asleep in minutes.
 */
class CalculateBedTimesUseCase {

    private val sleepCycleDurationMinutes = 90
    private val fallAsleepBufferMinutes = 15 //can be changed by user in future

    /**
     * Invokes the use case to calculate recommended bed times.
     *
     * @param wakeUpTime The time the user wants to wake up.
     * @return A list of sleep recommendations with labels and times.
     */
    operator fun invoke(wakeUpTime: LocalTime): List<SleepRecommendation> {
        return (6 downTo 3).map {cycles ->
            val totalSleepMinutes = cycles * sleepCycleDurationMinutes + fallAsleepBufferMinutes
            val recommendedTime = wakeUpTime.minusMinutes(totalSleepMinutes.toLong())
            SleepRecommendation(
                label = "${cycles * 1.5} hour of sleep",
                time = recommendedTime
            )
        }
    }
}