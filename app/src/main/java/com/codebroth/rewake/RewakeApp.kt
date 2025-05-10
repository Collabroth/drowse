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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.codebroth.rewake.core.navigation.RewakeNavHost
import com.codebroth.rewake.core.ui.components.appbars.BottomBar
import com.codebroth.rewake.core.ui.components.appbars.TopBar
import com.codebroth.rewake.core.ui.components.snackbar.ObserveAsEvents
import com.codebroth.rewake.core.ui.components.snackbar.SnackbarController
import com.codebroth.rewake.core.ui.theme.RewakeTheme
import kotlinx.coroutines.launch

@Composable
fun RewakeApp() {

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

    Scaffold (
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomBar(navController)
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

@Preview
@Composable
fun RewakeAppPreview() {
    RewakeTheme {
        RewakeApp()
    }
}