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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.codebroth.drowse.R

/**
 * Dialog to set the sleep buffer time, which is the time it takes to fall asleep.
 */
@Composable
fun SleepBufferDialog(
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit,
    currentValue: Int,
    modifier: Modifier = Modifier,
) {
    NumberPickerDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        onConfirmRequest = onConfirm,
        title = stringResource(R.string.title_set_fall_asleep_buffer),
        description = stringResource(R.string.description_sleep_buffer),
        currentValue = currentValue,
        defaultValue = 15,
        minValue = 0,
        maxValue = 30,
    )
}