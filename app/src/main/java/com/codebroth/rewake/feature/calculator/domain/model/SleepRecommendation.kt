package com.codebroth.rewake.feature.calculator.domain.model

import java.time.LocalTime


data class SleepRecommendation(
    val label: String,
    val time: LocalTime
)
