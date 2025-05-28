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

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.codebroth.drowse.R

/**
 * Dialog to set the sleep cycle length in minutes.
 */
@Composable
fun SleepCycleLengthDialog(
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit,
    currentValue: Int,
) {
    NumberPickerDialog(
        onDismissRequest = onDismiss,
        onConfirmRequest = onConfirm,
        title = stringResource(R.string.title_set_sleep_cycle_length),
        description = stringResource(R.string.description_set_sleep_cycle_length),
        currentValue = currentValue,
        defaultValue = 90,
        minValue = 70,
        maxValue = 110,
    )
}