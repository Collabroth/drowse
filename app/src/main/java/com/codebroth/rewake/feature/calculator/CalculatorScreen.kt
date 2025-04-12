package com.codebroth.rewake.feature.calculator

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrightnessHigh
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.outlined.BrightnessHigh
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {

    val calculatorTabItems = listOf(
        CalculatorTabItem(
            title = "Wake Up At",
            selectedIcon = Icons.Default.BrightnessHigh,
            unselectedIcon = Icons.Outlined.BrightnessHigh
        ),
        CalculatorTabItem(
            title = "Sleep At",
            selectedIcon = Icons.Default.DarkMode,
            unselectedIcon = Icons.Outlined.DarkMode
        )
    )

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    Column(
        modifier = modifier
    ) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            calculatorTabItems.forEachIndexed { index, item ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = {
                        selectedTabIndex = index
                    },
                    text = {
                        Text(text = item.title)
                    },
                    icon = {
                        Icon(
                            imageVector = if (index == selectedTabIndex) {
                                item.selectedIcon
                            } else {
                                item.unselectedIcon
                            },
                            contentDescription = item.title
                        )
                    }
                )
            }
        }
    }
}

data class CalculatorTabItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)