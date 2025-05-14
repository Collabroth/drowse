package com.codebroth.rewake.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.codebroth.rewake.alarm.ui.AlarmScreen
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
            CalculatorScreen(navController)
        }
        composable<AppDestination.Alarms> {
            val args = it.toRoute<AppDestination.Alarms>()
            AlarmScreen(
                setAlarmHour = args.setAlarmHour,
                setAlarmMinute = args.setAlarmMinutes
            )
        }
        composable<AppDestination.Reminders> {
            val args = it.toRoute<AppDestination.Reminders>()
            ReminderScreen(
                setReminderHour = args.setReminderHour,
                setReminderMinute = args.setReminderMinutes
            )
        }
//        composable<AppDestination.Settings> {
//            SettingsScreen()
//        }
    }
}