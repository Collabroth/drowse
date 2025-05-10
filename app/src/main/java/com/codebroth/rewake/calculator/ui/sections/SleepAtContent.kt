package com.codebroth.rewake.calculator.ui.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.codebroth.rewake.R
import com.codebroth.rewake.calculator.domain.model.SleepRecommendation
import com.codebroth.rewake.calculator.domain.usecase.CalculateWakeTimesUseCase
import com.codebroth.rewake.calculator.ui.components.SuggestionCard
import com.codebroth.rewake.calculator.ui.components.TimeInputButton
import com.codebroth.rewake.calculator.ui.components.TimeInputDialog
import com.codebroth.rewake.core.domain.util.TimeUtils
import com.codebroth.rewake.core.navigation.AppDestination
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SleepAtContent(navController: NavHostController) {

    var showDialog by remember { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState(
        initialHour = 22,
        initialMinute = 30,
        is24Hour = false,
    )

    var selectedBedTime by remember {
        mutableStateOf(LocalTime.of(timePickerState.hour, timePickerState.minute))
    }

    var recommendations by remember { mutableStateOf(emptyList<SleepRecommendation>()) }

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

        TextButton(
            onClick = {
                selectedBedTime = LocalTime.now()
                recommendations = useCase(selectedBedTime)
            }
        ) {
            Text("Sleep Now ->")
        }

        if (recommendations.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(stringResource(R.string.title_recommended_wake_times), style = MaterialTheme.typography.bodyLarge)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement   = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            ) {
                items(recommendations) { rec ->
                    SuggestionCard(
                        rec,
                        onSchedule = { time ->
                            navController.navigate(AppDestination.Alarms(time.hour, time.minute)) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop
                            }
                        },
                        isWakeTimes = false
                    )
                }
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
                recommendations = useCase(selectedBedTime)
                showDialog = false
            }
        ) {
            TimeInput(state = timePickerState)
        }
    }
}