package com.codebroth.rewake.calculator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.codebroth.rewake.R
import com.codebroth.rewake.calculator.ui.components.CalculatorMode
import com.codebroth.rewake.calculator.ui.components.RecommendationCard
import com.codebroth.rewake.calculator.ui.components.TimePickerButton
import com.codebroth.rewake.core.domain.util.TimeUtils
import com.codebroth.rewake.core.ui.components.input.DialTimePickerDialog
import com.codebroth.rewake.core.ui.navigation.AppDestination
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(
    navController: NavHostController,
    viewModel: CalculatorViewModel = hiltViewModel()
) {
    val uiState by viewModel.calculatorUiState.collectAsState()
    val isWakeUpMode = uiState.mode == CalculatorMode.WAKE_UP_TIME

    val promptMessage = stringResource(
        if (isWakeUpMode) {
            R.string.label_wake_up_prompt
        } else {
            R.string.label_sleep_at_prompt
        }
    )

    val recommendationsTitle = stringResource(
        if (isWakeUpMode) {
            R.string.title_recommended_bed_times
        } else {
            R.string.title_recommended_wake_times
        }
    )

    val timePickerInitial = if (isWakeUpMode) {
        LocalTime.of(7, 30)
    } else {
        LocalTime.of(22, 0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            FilterChip(
                selected = uiState.mode == CalculatorMode.WAKE_UP_TIME,
                onClick = { viewModel.onModeChange(CalculatorMode.WAKE_UP_TIME) },
                label = { Text(stringResource(R.string.wake_up_tab_name)) }
            )
            Spacer(Modifier.width(8.dp))
            FilterChip(
                selected = uiState.mode == CalculatorMode.BED_TIME,
                onClick = { viewModel.onModeChange(CalculatorMode.BED_TIME) },
                label = { Text(stringResource(R.string.sleep_at_tab_name)) }
            )
        }
        if (uiState.recommendations.isEmpty()) {
            Spacer(Modifier.weight(1f))
        }
        Text(
            text = promptMessage,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        TimePickerButton(
            onClick = { viewModel.onShowTimePicker(true) },
            timeText = TimeUtils.formatTime(timePickerInitial)
        )
        if (uiState.recommendations.isNotEmpty()) {
            Text(
                text = recommendationsTitle,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.recommendations) { rec ->
                    RecommendationCard(
                        rec = rec,
                        onClick = { time ->
                            navController.navigate(
                                if (uiState.mode == CalculatorMode.WAKE_UP_TIME)
                                    AppDestination.Reminders(time.hour, time.minute)
                                else
                                    AppDestination.Alarms(time.hour, time.minute)
                            ) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop
                            }
                        }
                    )
                }
            }
        }
        if (uiState.recommendations.isEmpty()) {
            Spacer(Modifier.weight(1f))
        }
    }
    if (uiState.showTimePicker) {
        DialTimePickerDialog(
            onConfirm = { timePickerState ->
                viewModel.onTimeSelected(TimeUtils.getTimeFromPickerState(timePickerState))
            },
            onDismissRequest = { viewModel.onShowTimePicker(false) },
            initialHour = timePickerInitial.hour,
            initialMinute = timePickerInitial.minute,
        )
    }
}
