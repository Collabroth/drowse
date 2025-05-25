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
 *
 * @property sleepCycleDurationMinutes Duration of a single sleep cycle in minutes.
 * @property fallAsleepBufferMinutes Buffer time to fall asleep in minutes.
 */
class CalculateWakeTimesUseCase {

    private val sleepCycleDurationMinutes = 90
    private val fallAsleepBufferMinutes = 15 //can be changed by user in future

    /**
     * Invokes the use case to calculate recommended wake up times.
     *
     * @param wakeUpTime The time the user wants to wake up.
     * @return A list of sleep recommendations based on the provided wake up time.
     */
    operator fun invoke(wakeUpTime: LocalTime): List<SleepRecommendation> {
        return (1..6).map { cycles ->
            val totalSleepMinutes = cycles * sleepCycleDurationMinutes + fallAsleepBufferMinutes
            val recommendedTime = wakeUpTime.plusMinutes(totalSleepMinutes.toLong())
            SleepRecommendation(cycles, cycles * 1.5, recommendedTime)
        }
    }
}