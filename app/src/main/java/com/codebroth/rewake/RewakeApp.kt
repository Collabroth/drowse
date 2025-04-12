package com.codebroth.rewake

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.codebroth.rewake.feature.calculator.CalculatorScreen
import com.codebroth.rewake.ui.components.RewakeAppBar

@Composable
fun RewakeApp() {
    Scaffold(
        topBar ={
            RewakeAppBar()
        }
    ) {
        CalculatorScreen(
            modifier = Modifier.fillMaxSize()
                .padding(it)
        )
    }
}