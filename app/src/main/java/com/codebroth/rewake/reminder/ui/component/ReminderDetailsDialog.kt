package com.codebroth.rewake.reminder.ui.component

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.codebroth.rewake.R
import com.codebroth.rewake.core.domain.util.TimeUtils
import com.codebroth.rewake.core.domain.util.TimeUtils.summarizeSelectedDaysOfWeek
import com.codebroth.rewake.core.ui.component.input.DialTimePickerDialog
import com.codebroth.rewake.reminder.domain.model.Reminder
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ReminderDetailsDialog(
    initial: Reminder?,
    onCancel: () -> Unit,
    onConfirm: (Reminder) -> Unit
) {

    var label by rememberSaveable { mutableStateOf(initial?.label.orEmpty()) }
    var days by rememberSaveable { mutableStateOf(initial?.daysOfWeek.orEmpty()) }
    var hour by rememberSaveable { mutableIntStateOf(initial?.hour ?: 21) }
    var minute by rememberSaveable { mutableIntStateOf(initial?.minute ?: 0) }

    var showPicker by rememberSaveable { mutableStateOf(false) }

    var expanded by rememberSaveable { mutableStateOf(false) }

    AlertDialog(
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
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                    label = { Text(stringResource(R.string.details_dialog_label_field)) },
                    value = label,
                    onValueChange = { label = it },
                )
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
                            contentDescription = "Select Time",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.size(12.dp))
                        Column {
                            Text(
                                text = "Time",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = TimeUtils.formatTime(LocalTime.of(hour, minute)),
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
                Text(stringResource(R.string.action_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(stringResource(R.string.action_dismiss))
            }
        }
    )
    if (showPicker) {
        DialTimePickerDialog(
            initialHour = hour,
            initialMinute = minute,
            onConfirm = { state ->
                hour = state.hour
                minute = state.minute
                showPicker = false
            },
            onDismissRequest = { showPicker = false }
        )
    }
}
