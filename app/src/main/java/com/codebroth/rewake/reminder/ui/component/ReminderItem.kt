package com.codebroth.rewake.reminder.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codebroth.rewake.R
import com.codebroth.rewake.reminder.domain.model.Reminder
import com.codebroth.rewake.reminder.domain.model.formattedTime

@Composable
fun ReminderItem(
    reminder: Reminder,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    is24Hour: Boolean = false,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                reminder.label?.let { lbl ->
                    Text(
                        text = lbl,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Text(
                    text = reminder.formattedTime(is24Hour),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = reminder.daysOfWeek.joinToString { it.name.take(3) },
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.description_action_delete_reminder)
                )
            }
        }
    }
}

@Preview
@Composable
fun ReminderItemPreview() {
    ReminderItem(
        reminder = Reminder(
            id = 1,
            hour = 12,
            minute = 30,
            daysOfWeek = setOf(),
            label = "Test Reminder"
        ),
        onClick = {},
        onDelete = {}
    )
}