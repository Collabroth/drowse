package com.codebroth.rewake.calculator.ui.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.codebroth.rewake.R
import com.codebroth.rewake.calculator.domain.usecase.CalculateWakeTimesUseCase
import com.codebroth.rewake.calculator.domain.util.TimeUtils
import com.codebroth.rewake.calculator.ui.components.TimeInputButton
import com.codebroth.rewake.calculator.ui.components.TimeInputDialog
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SleepAtContent() {

    var showDialog by remember { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState(
        initialHour = 22,
        initialMinute = 30,
        is24Hour = false,
    )

    var selectedBedTime by remember {
        mutableStateOf(LocalTime.of(timePickerState.hour, timePickerState.minute))
    }

    var recommendedWakeTimes by remember { mutableStateOf(emptyList<LocalTime>()) }

    val useCase = remember { CalculateWakeTimesUseCase() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
    ) {
        Text(stringResource(R.string.label_sleep_at_prompt))
        TimeInputButton(
            timeText = TimeUtils.formatTime(selectedBedTime),
            onClick = { showDialog = true }
        )

        if (recommendedWakeTimes.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(stringResource(R.string.title_recommended_wake_times))
            recommendedWakeTimes.forEach { time ->
                Text(
                    text = TimeUtils.formatTime(time),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }

    if (showDialog) {
        TimeInputDialog(
            onDismiss = { showDialog = false},
            onConfirm = {
                selectedBedTime = LocalTime.of(
                    timePickerState.hour,
                    timePickerState.minute
                )
                recommendedWakeTimes = useCase(selectedBedTime).map { it.time }
                showDialog = false
            }
        ) {
            TimeInput(state = timePickerState)
        }
    }
}