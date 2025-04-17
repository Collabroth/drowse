package com.codebroth.rewake.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppDestination {

    @Serializable
    object Calculator : AppDestination

    @Serializable
    object Reminders : AppDestination

    @Serializable
    object Settings : AppDestination

}
