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
import com.codebroth.rewake.feature.calculator.ui.CalculatorScreen
import com.codebroth.rewake.ui.components.RewakeAppBar
import com.codebroth.rewake.ui.theme.RewakeTheme

@Composable
fun RewakeApp() {

    val navigationBarItems = listOf(
        BottomNavigationItem(
            title = "Calculator",
            selectedIcon = Icons.Default.Analytics,
            unselectedIcon = Icons.Filled.Analytics
        ),
        BottomNavigationItem(
            title = "Alarms",
            selectedIcon = Icons.Default.Alarm,
            unselectedIcon = Icons.Filled.Alarm
        ),
        BottomNavigationItem(
            title = "Reminders",
            selectedIcon = Icons.Default.Notifications,
            unselectedIcon = Icons.Filled.Notifications
        )
    )

    var selectedNavBarItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    Scaffold (
        topBar = {
            RewakeAppBar()
        },
        bottomBar = {
            NavigationBar {
                navigationBarItems.forEachIndexed() { index, item ->
                    NavigationBarItem(
                        selected = selectedNavBarItemIndex == index,
                        onClick = {
                            selectedNavBarItemIndex = index
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedNavBarItemIndex) {
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
    ) { innerPadding ->
        CalculatorScreen(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
        )
    }
}

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Preview
@Composable
fun RewakeAppPreview() {
    RewakeTheme {
        RewakeApp()
    }
}