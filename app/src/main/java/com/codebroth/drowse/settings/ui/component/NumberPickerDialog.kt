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

package com.codebroth.drowse.settings.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.codebroth.drowse.R
import com.codebroth.drowse.core.ui.theme.DrowseTheme

/**
 * A dialog for picking a number within a specified range.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberPickerDialog(
    onDismissRequest: () -> Unit,
    onConfirmRequest: (Int) -> Unit,
    title: String,
    description: String,
    currentValue: Int,
    defaultValue: Int,
    minValue: Int,
    maxValue: Int,
    modifier: Modifier = Modifier
) {

    var textValue by rememberSaveable { mutableStateOf(currentValue.toString()) }
    var isError by rememberSaveable { mutableStateOf(false) }

    val parsedValue = textValue.toIntOrNull()
    val isValidValue = parsedValue != null && parsedValue in minValue..maxValue

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.padding_large)),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = dimensionResource(R.dimen.card_elevation)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_large)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_large))
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = textValue,
                    onValueChange = { newValue ->
                        if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                            textValue = newValue
                            isError = false
                        }
                    },
                    label = {
                        Text(stringResource(R.string.label_value_minutes))
                    },
                    supportingText = {
                        when {
                            isError || !isValidValue -> {
                                Text(
                                    text = stringResource(
                                        R.string.error_range_description,
                                        minValue,
                                        maxValue
                                    ),
                                    color = MaterialTheme.colorScheme.error
                                )
                            }

                            else -> {
                                Text(
                                    text = description,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    },
                    isError = isError || !isValidValue,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.small,
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    TextButton(
                        onClick = {
                            textValue = defaultValue.toString()
                            isError = false
                        }
                    ) { Text(stringResource(R.string.action_reset)) }
                    Spacer(Modifier.weight(1f))
                    TextButton(
                        onClick = onDismissRequest,
                    ) { Text(stringResource(R.string.action_cancel)) }
                    TextButton(
                        onClick = {
                            if (isValidValue) {
                                onConfirmRequest(parsedValue)
                            } else {
                                isError = true
                            }
                        },
                    ) { Text(stringResource(R.string.action_ok)) }
                }

            }
        }
    }
}

@Preview
@Composable
fun NumberPickerDialogPreview() {
    var value by rememberSaveable { mutableIntStateOf(90) }
    DrowseTheme {
        NumberPickerDialog(
            onDismissRequest = {},
            onConfirmRequest = { value = it },
            title = "Set Sleep Cycle Length",
            description = "Length of one sleep cycle",
            currentValue = value,
            defaultValue = 90,
            minValue = 70,
            maxValue = 110
        )
    }
}

@Preview
@Composable
fun NumberPickerDialogDarkThemePreview() {
    var value by rememberSaveable { mutableIntStateOf(90) }
    DrowseTheme(darkTheme = true) {
        NumberPickerDialog(
            onDismissRequest = {},
            onConfirmRequest = { value = it },
            title = "Set Sleep Cycle Length",
            description = "Length of one sleep cycle",
            currentValue = value,
            defaultValue = 90,
            minValue = 70,
            maxValue = 110
        )
    }
}