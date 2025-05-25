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

package com.codebroth.rewake

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.codebroth.rewake.core.data.local.UserPreferencesRepository
import com.codebroth.rewake.core.ui.component.appbar.BottomBar
import com.codebroth.rewake.core.ui.component.appbar.TopBar
import com.codebroth.rewake.core.ui.component.snackbar.ObserveAsEvents
import com.codebroth.rewake.core.ui.component.snackbar.SnackbarController
import com.codebroth.rewake.core.ui.navigation.RewakeNavHost
import kotlinx.coroutines.launch

@Composable
fun RewakeApp(userPreferencesRepository: UserPreferencesRepository) {

    val navController = rememberNavController()

    val snackBarHostState = remember { SnackbarHostState() }

    val snackBarScope = rememberCoroutineScope()

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
            TopBar()
        },
        bottomBar = {
            BottomBar(
                navController = navController,
                userPreferencesFlow = userPreferencesRepository.userPreferencesFlow
            )
        },
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        }
    ) { innerPadding ->
        RewakeNavHost(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}