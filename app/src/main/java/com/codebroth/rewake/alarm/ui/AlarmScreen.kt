package com.codebroth.rewake.alarm.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
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
import com.codebroth.rewake.R
import com.codebroth.rewake.alarm.domain.model.Alarm
import com.codebroth.rewake.alarm.domain.model.formattedTime
import com.codebroth.rewake.alarm.ui.component.AlarmDetailsDialog

@Composable
fun AlarmScreen(
    modifier: Modifier = Modifier,
    viewModel: AlarmViewModel = hiltViewModel(),
    setAlarmHour: Int? = null,
    setAlarmMinute: Int? = null
) {
    val uiState by viewModel.alarmUiState.collectAsState()
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
            onDelete = { viewModel.onDeleteAlarm(it) }
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
                    onToggle = { enabled -> viewModel.onToggleAlarm(alarm, enabled) }
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
                contentDescription = "Add Alarm"
            )
        }
    }
}

@Composable
private fun AlarmItem(
    alarm: Alarm,
    onClick: () -> Unit,
    onToggle: (Boolean) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                alarm.label?.let { lbl ->
                    Text(
                        text = lbl,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Text(
                    text = alarm.formattedTime(),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = alarm.daysOfWeek.joinToString { it.name.take(3) },
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            Switch(
                checked = alarm.isEnabled,
                onCheckedChange = { onToggle(it) },
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}