package com.codebroth.rewake.reminder.ui

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
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
import com.codebroth.rewake.R
import com.codebroth.rewake.core.domain.util.TimeUtils
import com.codebroth.rewake.reminder.domain.model.Reminder
import java.time.DayOfWeek
import java.time.LocalTime

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddEditReminderDialog(
    initial: Reminder?,
    onCancel:  () -> Unit,
    onConfirm: (Reminder) -> Unit
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

    AlertDialog(
        onDismissRequest = onCancel,
        title = {
            Text(
                if (initial == null) {
                    stringResource(R.string.action_add_reminder)
                } else {
                    stringResource(R.string.action_edit_reminder)
                }
        ) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = label,
                    onValueChange = { label = it },
                    label = { Text(stringResource(R.string.reminder_dialog_label)) },
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
}
