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

package com.codebroth.drowse.calculator.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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

@Composable
fun TimePickerButton(
    timeText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(dimensionResource(R.dimen.time_picker_button_height))
            .widthIn(dimensionResource(R.dimen.time_picker_button_max_width)),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = timeText,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.width(dimensionResource(R.dimen.spacer_small)))
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = stringResource(R.string.label_select_time)
            )
        }
    }
}

@Preview
@Composable
fun TimePickerButtonPreview() {
    DrowseTheme {
        TimePickerButton(
            timeText = "08:00 AM",
            onClick = {}
        )
    }
}

@Preview
@Composable
fun TimePickerButtonDarkThemePreview() {
    DrowseTheme(darkTheme = true) {
        TimePickerButton(
            timeText = "08:00 AM",
            onClick = {}
        )
    }
}