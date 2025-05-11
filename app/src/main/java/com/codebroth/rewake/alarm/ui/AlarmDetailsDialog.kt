package com.codebroth.rewake.alarm.ui

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.codebroth.rewake.R
import com.codebroth.rewake.alarm.domain.model.Alarm
import com.codebroth.rewake.core.domain.util.TimeUtils
import java.time.DayOfWeek
import java.time.LocalTime

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AlarmDetailsDialog(
    initial: Alarm?,
    onCancel: () -> Unit,
    onConfirm: (Alarm) -> Unit,
    onDelete: (Alarm) -> Unit
) {
    var label by rememberSaveable { mutableStateOf(initial?.label.orEmpty()) }
    var days  by rememberSaveable { mutableStateOf(initial?.daysOfWeek.orEmpty()) }
    var hour  by rememberSaveable { mutableIntStateOf(initial?.hour ?: 21) }
    var minute by rememberSaveable { mutableIntStateOf(initial?.minute ?: 0) }

    val context = LocalContext.current
    val timePicker = remember {
        TimePickerDialog(
            context,
            { _, h, m -> hour = h; minute = m },
            hour,
            minute,
            false
        )
    }

    Dialog(onDismissRequest = onCancel) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = if (initial == null) {
                        stringResource(R.string.action_add_alarm)
                    } else {
                        stringResource(R.string.action_edit_alarm)
                    },
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = label,
                    onValueChange = { label = it },
                    label = { Text(stringResource(R.string.details_dialog_label_field)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement   = Arrangement.spacedBy(8.dp)
                ) {
                    DayOfWeek.entries.forEach { day ->
                        FilterChip(
                            selected = day in days,
                            onClick = {
                                days = if (day in days) days - day else days + day
                            },
                            label = { Text(day.name.take(3)) }
                        )
                    }
                }
                Button(onClick = { timePicker.show() }) {
                    Text(text = TimeUtils.formatTime(LocalTime.of(hour, minute)))
                }

                Spacer(Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (initial != null) {
                        TextButton(
                            onClick = { onDelete(initial) },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Text(stringResource(R.string.action_delete))
                        }
                    } else {
                        Spacer(Modifier.width(8.dp))
                    }

                    Row {
                        TextButton(onClick = onCancel) {
                            Text(stringResource(R.string.action_dismiss))
                        }
                        TextButton(
                            onClick = {
                                onConfirm(
                                    Alarm(
                                        id = initial?.id ?: 0,
                                        hour = hour,
                                        minute = minute,
                                        daysOfWeek = days,
                                        label = label.ifBlank { null },
                                        isEnabled = initial?.isEnabled != false
                                    )
                                )
                            }
                        ) {
                            Text(stringResource(R.string.action_confirm))
                        }
                    }
                }
            }
        }
    }
}