package com.codebroth.rewake.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class AppDestination() {
    @Serializable
    object Calculator : AppDestination()

    @Serializable
    data class Reminders(
        val setReminderHour: Int? = null,
        val setReminderMinutes: Int? = null
    ) : AppDestination()

    @Serializable
    data class Alarms(
        val setAlarmHour: Int? = null,
        val setAlarmMinutes: Int? = null
    ) : AppDestination()

    @Serializable
    object Settings : AppDestination()
}

