package com.codebroth.rewake.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.codebroth.rewake.navigation.AppDestination
import com.codebroth.rewake.navigation.AppDestination.*

@Composable
fun BottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val navigationBarItems = listOf(
        BottomNavigationItem(
            title = "Calculator",
            destination = Calculator,
            selectedIcon = Icons.Default.Analytics,
            unselectedIcon = Icons.Outlined.Analytics
        ),
        BottomNavigationItem(
            title = "Reminders",
            destination = Reminders,
            selectedIcon = Icons.Default.Notifications,
            unselectedIcon = Icons.Outlined.Notifications
        ),
        BottomNavigationItem(
            title = "Settings",
            destination = Settings,
            selectedIcon = Icons.Default.Settings,
            unselectedIcon = Icons.Outlined.Settings
        )
    )

    var selectedNavBarItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        navigationBarItems.forEachIndexed() { index, item ->
            NavigationBarItem(
                selected = selectedNavBarItemIndex == index,
                onClick = {
                    selectedNavBarItemIndex = index

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

data class BottomNavigationItem(
    val title: String,
    val destination: AppDestination,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
