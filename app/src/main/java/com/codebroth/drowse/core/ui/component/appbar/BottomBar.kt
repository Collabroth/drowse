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

package com.codebroth.drowse.core.ui.component.appbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.codebroth.drowse.R
import com.codebroth.drowse.core.domain.model.UserPreferences
import com.codebroth.drowse.core.ui.navigation.AppDestination
import com.codebroth.drowse.core.ui.navigation.AppDestination.CalculatorDestination
import com.codebroth.drowse.core.ui.navigation.AppDestination.ReminderDestination
import com.codebroth.drowse.core.ui.navigation.AppDestination.SettingsDestination
import kotlinx.coroutines.flow.Flow

@Composable
private fun getNavbarItems() = listOf(
    BottomNavigationItem(
        title = stringResource(R.string.title_calculator),
        destination = CalculatorDestination,
        selectedIcon = Icons.Default.Analytics,
        unselectedIcon = Icons.Outlined.Analytics
    ),
    BottomNavigationItem(
        title = stringResource(R.string.title_reminders),
        destination = ReminderDestination(),
        selectedIcon = Icons.Default.Notifications,
        unselectedIcon = Icons.Outlined.Notifications
    ),
    BottomNavigationItem(
        title = stringResource(R.string.title_settings),
        destination = SettingsDestination,
        selectedIcon = Icons.Default.Settings,
        unselectedIcon = Icons.Outlined.Settings
    )
)

@Composable
fun BottomBar(
    navController: NavHostController,
    userPreferencesFlow: Flow<UserPreferences>,
    modifier: Modifier = Modifier,
) {
    val userPreferences by userPreferencesFlow.collectAsState(initial = UserPreferences.DEFAULT)

    val navigationBarItems = getNavbarItems()

    NavigationBar(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination?.let { dest ->
            when {
                dest.hasRoute<CalculatorDestination>() -> CalculatorDestination
                dest.hasRoute<ReminderDestination>() -> ReminderDestination()
                dest.hasRoute<SettingsDestination>() -> SettingsDestination
                else -> null
            }
        }

        navigationBarItems.forEachIndexed { index, item ->
            val isSelected = when {
                item.destination is CalculatorDestination && currentDestination is CalculatorDestination -> true
                item.destination is ReminderDestination && currentDestination is ReminderDestination -> true
                item.destination is SettingsDestination && currentDestination is SettingsDestination -> true
                else -> false
            }
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.destination) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) {
                            item.selectedIcon
                        } else {
                            item.unselectedIcon
                        },
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(
                        text = item.title
                    )
                }
            )
        }
    }
}

data class BottomNavigationItem(
    val title: String,
    val destination: AppDestination,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
