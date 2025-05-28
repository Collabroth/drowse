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

package com.codebroth.drowse.reminder.ui.component

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.codebroth.drowse.R
import com.codebroth.drowse.core.domain.util.TimeUtils
import com.codebroth.drowse.core.domain.util.TimeUtils.summarizeSelectedDaysOfWeek
import com.codebroth.drowse.core.ui.component.input.DialTimePickerDialog
import com.codebroth.drowse.reminder.domain.model.Reminder
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ReminderDetailsDialog(
    initial: Reminder?,
    onCancel: () -> Unit,
    onConfirm: (Reminder) -> Unit,
    modifier: Modifier = Modifier,
    is24Hour: Boolean = false,
) {

    var label by rememberSaveable { mutableStateOf(initial?.label.orEmpty()) }
    var days by rememberSaveable { mutableStateOf(initial?.daysOfWeek.orEmpty()) }
    var hour by rememberSaveable { mutableIntStateOf(initial?.hour ?: 21) }
    var minute by rememberSaveable { mutableIntStateOf(initial?.minute ?: 0) }

    var showPicker by rememberSaveable { mutableStateOf(false) }

    var expanded by rememberSaveable { mutableStateOf(false) }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onCancel,
        title = {
            Text(
                if (initial == null) {
                    stringResource(R.string.action_add_reminder)
                } else {
                    stringResource(R.string.action_edit_reminder)
                }
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_size_small)),
                    singleLine = true,
                    label = { Text(stringResource(R.string.details_dialog_label_field)) },
                    value = label,
                    onValueChange = { label = it },
                )
                OutlinedCard(
                    onClick = { showPicker = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_size_small)),
                    border = BorderStroke(
                        width = dimensionResource(R.dimen.border_width),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(R.dimen.padding_medium)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = stringResource(R.string.label_select_time),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.spacer_medium)))
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
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    border = BorderStroke(
                        width = dimensionResource(R.dimen.border_width),
                        color = MaterialTheme.colorScheme.outlineVariant
                    ),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_size_small))
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Surface(
                            onClick = { expanded = !expanded },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(dimensionResource(R.dimen.padding_medium)),
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
                                        stringResource(R.string.action_collapse_content_description)
                                    } else {
                                        stringResource(R.string.action_expand_content_description)
                                    },
                                    modifier = Modifier
                                        .padding(start = dimensionResource(R.dimen.padding_small))
                                        .size(dimensionResource(R.dimen.icon_size))
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
                                    .padding(dimensionResource(R.dimen.padding_small))
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
                                            .padding(
                                                vertical = dimensionResource(R.dimen.padding_xsmall),
                                                horizontal = dimensionResource(R.dimen.padding_small)
                                            ),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Spacer(Modifier.size(dimensionResource(R.dimen.spacer_small)))
                                        Text(
                                            text = day.getDisplayName(
                                                TextStyle.FULL,
                                                Locale.getDefault()
                                            ),
                                            style = MaterialTheme.typography.bodyMedium,
                                            modifier = Modifier.padding(start = dimensionResource(R.dimen.spacer_small))
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
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm(
                    Reminder(
                        id = initial?.id ?: 0,
                        hour = hour,
                        minute = minute,
                        daysOfWeek = days,
                        label = label.ifBlank { null }
                    )
                )
            }) {
                Text(stringResource(R.string.action_ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(stringResource(R.string.action_cancel))
            }
        }
    )
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

@Preview
@Composable
fun ReminderDetailsDialogPreview() {
    ReminderDetailsDialog(
        initial = null,
        onCancel = {},
        onConfirm = {}
    )
}
