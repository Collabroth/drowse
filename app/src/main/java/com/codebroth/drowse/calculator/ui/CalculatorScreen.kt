/*
 *
 *    Copyright 2025 Jayman Rana
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.codebroth.drowse.calculator.ui

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.codebroth.drowse.R
import com.codebroth.drowse.calculator.ui.component.CalculatorMode
import com.codebroth.drowse.calculator.ui.component.RecommendationCard
import com.codebroth.drowse.calculator.ui.component.TimePickerButton
import com.codebroth.drowse.core.domain.util.TimeUtils
import com.codebroth.drowse.core.ui.component.input.DialTimePickerDialog
import com.codebroth.drowse.core.ui.navigation.AppDestination

/**
 * The main screen for the calculator feature, allowing users to calculate sleep and wake times.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: CalculatorViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
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
            Spacer(Modifier.width(dimensionResource(R.dimen.spacer_small)))
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
        )
        TimePickerButton(
            onClick = { viewModel.onShowTimePicker(true) },
            timeText = TimeUtils.formatTime(uiState.selectedTime, uiState.is24HourFormat),
        )
        if (uiState.recommendations.isNotEmpty()) {
            Text(
                text = recommendationsTitle,
                style = MaterialTheme.typography.bodyLarge,
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.recommendations) { rec ->
                    RecommendationCard(
                        rec = rec,
                        onClick = { viewModel.sendAlarmClockIntent(it) },
                        iconImageVector = if (uiState.mode == CalculatorMode.WAKE_UP_TIME) {
                            Icons.Default.Notifications
                        } else {
                            Icons.Default.Alarm
                        },
                        is24Hour = uiState.is24HourFormat
                    )
                }
            }
        } else {
            if (uiState.mode == CalculatorMode.BED_TIME) {
                TextButton(
                    onClick = viewModel::onClickSleepNow
                ) {
                    Text("Sleep Now ->")
                }
            }
            Spacer(Modifier.weight(1f))
        }
    }
    if (uiState.showTimePicker) {
        DialTimePickerDialog(
            onConfirm = { timePickerState ->
                viewModel.onTimeSelected(
                    TimeUtils.getTimeFromHourMinute(
                        timePickerState.hour,
                        timePickerState.minute
                    )
                )
            },
            onDismissRequest = { viewModel.onShowTimePicker(false) },
            is24Hour = uiState.is24HourFormat,
            initialHour = uiState.selectedTime.hour,
            initialMinute = uiState.selectedTime.minute,
        )
    }
}
