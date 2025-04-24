package com.codebroth.rewake.calculator.domain.model

import java.time.LocalTime


data class SleepRecommendation(
    val label: String,
    val time: LocalTime
)
