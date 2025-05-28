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

package com.codebroth.drowse.core.ui.component.input

import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.codebroth.drowse.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialTimePickerDialog(
    onConfirm: (TimePickerState) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.label_select_time),
    is24Hour: Boolean = false,
    initialHour: Int? = null,
    initialMinute: Int? = null,
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = initialHour ?: currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = initialMinute ?: currentTime.get(Calendar.MINUTE),
        is24Hour = is24Hour
    )

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = dimensionResource(R.dimen.surface_tonal_elevation),
        ) {
            Column(
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = dimensionResource(R.dimen.padding_large)),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                TimePicker(
                    state = timePickerState
                )
                Row(
                    modifier = Modifier
                        .height(dimensionResource(R.dimen.dialog_button_row_height))
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = onDismissRequest) {
                        Text(stringResource(R.string.action_cancel))
                    }
                    TextButton(onClick = { onConfirm(timePickerState) }) {
                        Text(stringResource(R.string.action_ok))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DialTimePickerPreview() {
    DialTimePickerDialog(
        onConfirm = {},
        onDismissRequest = {}
    )
}