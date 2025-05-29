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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.codebroth.drowse.R
import com.codebroth.drowse.core.ui.theme.DrowseTheme
import com.codebroth.drowse.reminder.domain.model.Reminder
import com.codebroth.drowse.reminder.domain.model.formattedTime

@Composable
fun ReminderItem(
    reminder: Reminder,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    is24Hour: Boolean = false,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
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
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_xsmall))
                )
                Text(
                    text = reminder.daysOfWeek.joinToString { it.name.take(3) },
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_xsmall))
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
    DrowseTheme {
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
}

@Preview
@Composable
fun ReminderItemDarkThemePreview() {
    DrowseTheme(darkTheme = true) {
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
}