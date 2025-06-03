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

package com.codebroth.drowse.core.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.codebroth.drowse.alarm.ui.AlarmScreen
import com.codebroth.drowse.calculator.ui.CalculatorScreen
import com.codebroth.drowse.reminder.ui.ReminderScreen
import com.codebroth.drowse.settings.ui.SettingsScreen

/**
 * Navigation host for the Drowse app.
 * Defines the navigation graph and composable destinations.
 *
 * @param navController The NavHostController to manage navigation.
 * @param modifier Optional modifier for the NavHost.
 */
@Composable
fun DrowseNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppDestination.Calculator,
        modifier = modifier,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        }
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
        composable<AppDestination.Settings> {
            SettingsScreen()
        }
    }
}