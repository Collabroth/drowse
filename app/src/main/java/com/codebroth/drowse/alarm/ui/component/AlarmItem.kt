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

package com.codebroth.drowse.alarm.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.codebroth.drowse.R
import com.codebroth.drowse.alarm.domain.model.Alarm
import com.codebroth.drowse.alarm.domain.model.formattedTime
import java.time.DayOfWeek

@Composable
fun AlarmItem(
    alarm: Alarm,
    onClick: () -> Unit,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    is24Hour: Boolean = false,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
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
                    text = alarm.formattedTime(is24Hour),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_xsmall))
                )
                Text(
                    text = alarm.daysOfWeek.joinToString { it.name.take(3) },
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_xsmall))
                )
            }
            Switch(
                checked = alarm.isEnabled,
                onCheckedChange = { onToggle(it) },
                modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_small))
            )
        }
    }
}

@Preview
@Composable
fun AlarmItemPreview() {
    AlarmItem(
        alarm = Alarm(
            id = 1,
            hour = 7,
            minute = 30,
            daysOfWeek = setOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY),
            label = "Morning Alarm",
            isEnabled = true
        ),
        onClick = {},
        onToggle = {}
    )
}