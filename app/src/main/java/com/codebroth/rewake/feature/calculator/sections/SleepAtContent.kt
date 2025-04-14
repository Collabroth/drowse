package com.codebroth.rewake.feature.calculator.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codebroth.rewake.feature.calculator.components.TimeInputButton

@Composable
fun SleepAtContent() {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
    ) {
        Text("I want to sleep at...")
        TimeInputButton(
            timeText = "10:30 PM",
            onClick = {}
        )
    }
}