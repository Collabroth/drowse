package com.codebroth.rewake

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.codebroth.rewake.navigation.RewakeNavHost
import com.codebroth.rewake.ui.components.BottomBar
import com.codebroth.rewake.ui.components.TopBar
import com.codebroth.rewake.ui.theme.RewakeTheme

@Composable
fun RewakeApp() {

    val navController = rememberNavController()

    Scaffold (
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomBar(navController)
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