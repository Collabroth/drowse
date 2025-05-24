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

package com.codebroth.rewake.alarm.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.codebroth.rewake.R
import com.codebroth.rewake.alarm.domain.model.Alarm
import com.codebroth.rewake.core.domain.util.TimeUtils
import com.codebroth.rewake.core.domain.util.TimeUtils.summarizeSelectedDaysOfWeek
import com.codebroth.rewake.core.ui.component.input.DialTimePickerDialog
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AlarmDetailsDialog(
    initial: Alarm?,
    onCancel: () -> Unit,
    onConfirm: (Alarm) -> Unit,
    onDelete: (Alarm) -> Unit,
    is24Hour: Boolean = false,
) {
    var label by rememberSaveable { mutableStateOf(initial?.label.orEmpty()) }
    var days by rememberSaveable { mutableStateOf(initial?.daysOfWeek.orEmpty()) }
    var hour by rememberSaveable { mutableIntStateOf(initial?.hour ?: 21) }
    var minute by rememberSaveable { mutableIntStateOf(initial?.minute ?: 0) }

    var showPicker by rememberSaveable { mutableStateOf(false) }

    var expanded by rememberSaveable { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onCancel,
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
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
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                    label = { Text(stringResource(R.string.details_dialog_label_field)) },
                    value = label,
                    onValueChange = { label = it },
                )
                Spacer(Modifier.height(16.dp))
                OutlinedCard(
                    onClick = { showPicker = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = stringResource(R.string.label_select_time),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.size(12.dp))
                        Column {
                            Text(
                                text = stringResource(R.string.label_time),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = TimeUtils.formatTime(LocalTime.of(hour, minute), is24Hour),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Surface(
                            onClick = { expanded = !expanded },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(R.string.label_repeat),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(Modifier.weight(1f))
                                Text(
                                    text = summarizeSelectedDaysOfWeek(days),
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = if (expanded) {
                                        stringResource(R.string.description_action_collapse)
                                    } else {
                                        stringResource(R.string.description_action_expand)
                                    },
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .size(20.dp)
                                        .rotate(if (expanded) 180f else 0f),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        AnimatedVisibility(
                            visible = expanded,
                            enter = expandVertically(
                                animationSpec = tween(durationMillis = 300)
                            ) + fadeIn(
                                animationSpec = tween(durationMillis = 300)
                            ),
                            exit = shrinkVertically(
                                animationSpec = tween(durationMillis = 250)
                            ) + fadeOut(
                                animationSpec = tween(durationMillis = 200)
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp, vertical = 8.dp)
                            ) {
                                DayOfWeek.entries.forEach { day ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                days = if (day in days) {
                                                    days - day
                                                } else {
                                                    days + day
                                                }
                                            }
                                            .padding(vertical = 2.dp, horizontal = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Spacer(Modifier.size(8.dp))
                                        Text(
                                            text = day.getDisplayName(
                                                TextStyle.FULL,
                                                Locale.getDefault()
                                            ),
                                            style = MaterialTheme.typography.bodyMedium,
                                            modifier = Modifier.padding(start = 8.dp)
                                        )
                                        Spacer(Modifier.weight(1f))
                                        Checkbox(
                                            checked = day in days,
                                            onCheckedChange = null
                                        )
                                    }
                                }
                            }
                        }
                    }
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
                                        isEnabled = true
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
    if (showPicker) {
        DialTimePickerDialog(
            onConfirm = { state ->
                hour = state.hour
                minute = state.minute
                showPicker = false
            },
            onDismissRequest = { showPicker = false },
            is24Hour = is24Hour,
            initialHour = hour,
            initialMinute = minute,
        )
    }
}