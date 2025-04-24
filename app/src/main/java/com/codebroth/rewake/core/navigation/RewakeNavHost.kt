package com.codebroth.rewake.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codebroth.rewake.calculator.ui.CalculatorScreen
import com.codebroth.rewake.reminder.ui.ReminderScreen
import com.codebroth.rewake.settings.ui.SettingsScreen

@Composable
fun RewakeNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = AppDestination.Calculator,
        modifier = modifier
    ) {
        composable<AppDestination.Calculator> {
            CalculatorScreen()
        }
        composable<AppDestination.Reminders> {
            ReminderScreen()
        }
        composable<AppDestination.Settings> {
            SettingsScreen()
        }
    }
}