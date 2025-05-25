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

package com.codebroth.drowse.alarm.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codebroth.drowse.R
import com.codebroth.drowse.alarm.domain.model.Alarm
import com.codebroth.drowse.alarm.ui.component.AlarmDetailsDialog
import com.codebroth.drowse.alarm.ui.component.AlarmItem

@Composable
fun AlarmScreen(
    modifier: Modifier = Modifier,
    viewModel: AlarmViewModel = hiltViewModel(),
    setAlarmHour: Int? = null,
    setAlarmMinute: Int? = null
) {
    val uiState by viewModel.uiState.collectAsState()
    val alarms by viewModel.alarms.collectAsState()

    var hasConsumedArgs by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(setAlarmHour, setAlarmMinute) {
        if (
            !hasConsumedArgs
            && setAlarmHour != null
            && setAlarmMinute != null
        ) {
            viewModel.showDialog(
                Alarm(
                    id = 0,
                    hour = setAlarmHour,
                    minute = setAlarmMinute,
                    daysOfWeek = emptySet(),
                    label = null
                )
            )
            hasConsumedArgs = true
        }
    }

    if (uiState.isDialogOpen) {
        AlarmDetailsDialog(
            initial = uiState.editingAlarm,
            onCancel = { viewModel.dismissDialog() },
            onConfirm = { viewModel.onSaveAlarm(it) },
            onDelete = { viewModel.onDeleteAlarm(it) },
            is24Hour = uiState.is24HourFormat,
        )
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        if (alarms.isEmpty()) {
            Text(
                text = stringResource(R.string.empty_alarms_description),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(alarms, key = { it.id }) { alarm ->
                AlarmItem(
                    alarm = alarm,
                    onClick = { viewModel.showDialog(alarm) },
                    onToggle = { enabled -> viewModel.onToggleAlarm(alarm, enabled) },
                    is24Hour = uiState.is24HourFormat,
                )
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = { viewModel.showDialog() }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.action_add_alarm)
            )
        }
    }
}
