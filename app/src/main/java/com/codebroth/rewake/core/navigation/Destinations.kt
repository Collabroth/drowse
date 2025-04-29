package com.codebroth.rewake.core.navigation

import com.codebroth.rewake.reminder.domain.model.Reminder
import kotlinx.serialization.Serializable

@Serializable
sealed class AppDestination() {
    @Serializable
    object Calculator : AppDestination()

    @Serializable
    data class Reminders(val setReminderHour: Int? = null, val setReminderMinutes: Int? = null) : AppDestination()

    @Serializable
    object Settings : AppDestination()
}

