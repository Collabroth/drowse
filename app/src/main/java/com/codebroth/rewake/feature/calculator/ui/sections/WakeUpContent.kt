package com.codebroth.rewake.feature.calculator.ui.sections

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
import com.codebroth.rewake.feature.calculator.domain.usecase.CalculateBedTimesUseCase
import com.codebroth.rewake.feature.calculator.domain.util.TimeUtils
import com.codebroth.rewake.feature.calculator.ui.components.TimeInputButton
import com.codebroth.rewake.feature.calculator.ui.components.TimeInputDialog
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WakeUpContent() {

    var showDialog by remember { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState(
        initialHour = 7,
        initialMinute = 30,
        is24Hour = false,
    )

    var selectedWakeUpTime by remember {
        mutableStateOf(LocalTime.of(timePickerState.hour, timePickerState.minute))
    }

    var recommendedSleepTimes by remember {
        mutableStateOf(emptyList<LocalTime>())
    }

    val useCase = remember { CalculateBedTimesUseCase()}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
    ) {
        Text(stringResource(R.string.label_wake_up_prompt))
        TimeInputButton(
            timeText = TimeUtils.formatTime(selectedWakeUpTime),
            onClick = { showDialog = true }
        )

        if (recommendedSleepTimes.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(stringResource(R.string.title_recommended_bed_times))
            recommendedSleepTimes.forEach { time ->
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
                selectedWakeUpTime = LocalTime.of(
                    timePickerState.hour,
                    timePickerState.minute
                )
                recommendedSleepTimes = useCase(selectedWakeUpTime).map { it.time }
                showDialog = false
            }
        ) {
            TimeInput(state = timePickerState)
        }
    }
}