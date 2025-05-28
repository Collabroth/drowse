/*
 *
 *    Copyright 2025 Jayman Rana
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.codebroth.drowse.calculator.domain.usecase

import com.codebroth.drowse.calculator.domain.model.SleepRecommendation
import java.time.LocalTime

/**
 * Use case to calculate recommended wake up times based on sleep cycles.
 */
class CalculateWakeTimesUseCase {
    /**
     * Invokes the use case to calculate recommended wake up times.
     *
     * @param bedtime The time the user wants to go to bed.
     * @param sleepCycleDurationMinutes The duration of a single sleep cycle in minutes.
     * @param fallAsleepBufferMinutes The buffer time to fall asleep in minutes.
     * @return A list of [SleepRecommendation] containing the number of cycles, hours of sleep, and recommended wake up time.
     */
    operator fun invoke(
        bedtime: LocalTime,
        sleepCycleDurationMinutes: Int = 90,
        fallAsleepBufferMinutes: Int = 15,
    ): List<SleepRecommendation> {
        return (1..6).map { cycles ->
            val totalSleepMinutes = cycles * sleepCycleDurationMinutes + fallAsleepBufferMinutes
            val recommendedTime = bedtime.plusMinutes(totalSleepMinutes.toLong())
            SleepRecommendation(cycles, totalSleepMinutes, recommendedTime)
        }
    }
}