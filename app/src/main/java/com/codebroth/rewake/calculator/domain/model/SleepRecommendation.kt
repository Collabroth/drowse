package com.codebroth.rewake.calculator.domain.model

import java.time.LocalTime


data class SleepRecommendation(
    // 1 cycle approximately 90 minutes
    val cycles: Int,
    val hours: Double,
    val time: LocalTime
)
