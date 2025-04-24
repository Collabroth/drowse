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
import androidx.compose.ui.unit.dp
import com.codebroth.rewake.reminder.domain.model.Reminder
import java.time.DayOfWeek

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddEditReminderDialog(
    initial: Reminder?,            // null = new, otherwise we’re editing
    onCancel:  () -> Unit,
    onConfirm: (Reminder) -> Unit
) {
    // 1) Remember local state
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
            true
        )
    }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text(if (initial == null) "Add Reminder" else "Edit Reminder") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = label,
                    onValueChange = { label = it },
                    label = { Text("Label (optional)") },
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
                // Time picker button
                Button(onClick = { timePicker.show() }) {
                    Text(text = "%02d:%02d".format(hour, minute))
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
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Cancel")
            }
        }
    )
}
