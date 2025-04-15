package com.codebroth.rewake

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.codebroth.rewake.feature.calculator.ui.CalculatorScreen
import com.codebroth.rewake.ui.components.RewakeAppBar
import com.codebroth.rewake.ui.theme.RewakeTheme

@Composable
fun RewakeApp() {
    Scaffold (
        topBar = {
            RewakeAppBar()
        }
    ) { innerPadding ->
        CalculatorScreen(
            modifier = Modifier.fillMaxSize()
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