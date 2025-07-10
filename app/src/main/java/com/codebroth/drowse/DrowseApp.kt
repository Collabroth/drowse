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

package com.codebroth.drowse

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.codebroth.drowse.core.ui.component.appbar.BottomBar
import com.codebroth.drowse.core.ui.component.appbar.TopBar
import com.codebroth.drowse.core.ui.component.snackbar.ObserveAsEvents
import com.codebroth.drowse.core.ui.component.snackbar.SnackbarController
import com.codebroth.drowse.core.ui.navigation.AppDestination.CalculatorDestination
import com.codebroth.drowse.core.ui.navigation.AppDestination.ReminderDestination
import com.codebroth.drowse.core.ui.navigation.AppDestination.SettingsDestination
import com.codebroth.drowse.core.ui.navigation.DrowseNavHost
import kotlinx.coroutines.launch

@Composable
fun DrowseApp() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.let { dest ->
        when {
            dest.hasRoute<CalculatorDestination>() -> CalculatorDestination
            dest.hasRoute<ReminderDestination>() -> ReminderDestination()
            dest.hasRoute<SettingsDestination>() -> SettingsDestination
            else -> null
        }
    }

    val snackBarHostState = remember { SnackbarHostState() }
    val snackBarScope = rememberCoroutineScope()


    val screenTitle = when {
        currentDestination is CalculatorDestination -> stringResource(R.string.app_name)
        currentDestination is ReminderDestination -> stringResource(R.string.title_reminders)
        currentDestination is SettingsDestination -> stringResource(R.string.title_settings)
        else -> stringResource(R.string.app_name)
    }

    ObserveAsEvents(
        flow = SnackbarController.events,
        snackBarHostState
    ) { event ->
        snackBarScope.launch {
            snackBarHostState.currentSnackbarData?.dismiss()

            val result = snackBarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.name,
                duration = SnackbarDuration.Short
            )

            if (result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }

    Scaffold(
        topBar = {
            TopBar(screenTitle)
        },
        bottomBar = {
            BottomBar(navController)
        },
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        DrowseNavHost(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}