package com.codebroth.rewake.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codebroth.rewake.feature.calculator.ui.CalculatorScreen
import com.codebroth.rewake.feature.reminder.ui.ReminderScreen
import com.codebroth.rewake.feature.settings.ui.SettingsScreen
import com.codebroth.rewake.navigation.AppDestination.*

@Composable
fun RewakeNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Calculator,
        modifier = modifier
    ) {
        composable<Calculator> {
            CalculatorScreen()
        }
        composable<Reminders> {
            ReminderScreen()
        }
        composable<Settings> {
            SettingsScreen()
        }
    }
}