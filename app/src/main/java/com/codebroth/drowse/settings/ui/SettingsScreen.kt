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

package com.codebroth.drowse.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Loop
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.codebroth.drowse.R
import com.codebroth.drowse.core.domain.model.ThemePreference
import com.codebroth.drowse.settings.ui.component.PreferenceRow
import com.codebroth.drowse.settings.ui.component.SettingsSection
import com.codebroth.drowse.settings.ui.component.SleepBufferDialog
import com.codebroth.drowse.settings.ui.component.SleepCycleLengthDialog
import com.codebroth.drowse.settings.ui.component.ThemeDropDownMenu

/**
 * Enum class to represent the active dialog in the settings screen.
 * This is used to manage which dialog is currently displayed to the user.
 */
private enum class ActiveDialog { Buffer, CycleLength }

/**
 * The main settings screen for the application.
 *
 * This screen allows users to configure preferences related to the app's functionality,
 * such as time format, sleep settings, and displays developer contact options or link to the source code.
 *
 * @param viewModel The ViewModel that provides the UI state and handles user interactions.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var activeDialog by rememberSaveable { mutableStateOf<ActiveDialog?>(null) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_large)),
        contentPadding = PaddingValues(vertical = dimensionResource(R.dimen.padding_medium))
    ) {
        item {
            SettingsSection(stringResource(R.string.title_appearance)) {
                var isDropDownExpanded by rememberSaveable { mutableStateOf(false) }
                val selectedTheme = ThemePreference
                    .entries
                    .firstOrNull { it.value == uiState.themePreference }
                    ?: ThemePreference.AUTO
                PreferenceRow(
                    icon = Icons.Default.Palette,
                    label = stringResource(R.string.label_theme),
                    trailingContent = {
                        ThemeDropDownMenu(
                            selectedTheme = selectedTheme,
                            isDropDownExpanded = isDropDownExpanded,
                            onExpandedChanged = { isDropDownExpanded = it },
                            onSelected = {
                                viewModel.onClickThemeDropDown(it)
                            }
                        )
                    }
                )
                PreferenceRow(
                    icon = Icons.Default.AutoAwesome, //Colorize
                    label = stringResource(R.string.label_use_dynamic_color),
                    description = stringResource(R.string.dynamic_color_description),
                    trailingContent = {
                        Switch(
                            checked = uiState.useDynamicColor,
                            onCheckedChange = viewModel::onToggleUseDynamicColor
                        )
                    }
                )
            }
        }
        item {
            SettingsSection(stringResource(R.string.title_preferences)) {
                PreferenceRow(
                    icon = Icons.Default.AccessTime,
                    label = stringResource(R.string.label_time_format),
                    trailingContent = {
                        Switch(
                            checked = uiState.is24HourFormat,
                            onCheckedChange = viewModel::onToggle24HourFormat
                        )
                    },
                )
                // Not yet ready for production. Alarm feature is a work in progress.
                PreferenceRow(
                    icon = Icons.Default.Alarm,
                    label = stringResource(R.string.label_use_system_alarm_app),
                    description = "recommended to keep on",
                    trailingContent = {
                        Switch(
                            checked = uiState.useAlarmClockApi,
                            onCheckedChange = viewModel::onToggleUseAlarmClockApi
                        )
                    }
                )
                PreferenceRow(
                    icon = Icons.Default.Info,
                    label = stringResource(R.string.label_note),
                    description = stringResource(R.string.temporary_resource_alarm_feature_note)
                )
            }
        }
        item {
            SettingsSection(stringResource(R.string.title_sleep)) {
                PreferenceRow(
                    icon = Icons.Default.Bedtime,
                    label = stringResource(R.string.label_fall_asleep_buffer),
                    description = stringResource(
                        R.string.parameter_value_minutes,
                        uiState.fallAsleepBuffer
                    ),
                    onClick = { activeDialog = ActiveDialog.Buffer },
                )
                PreferenceRow(
                    icon = Icons.Default.Loop,
                    label = stringResource(R.string.label_sleep_cycle_length),
                    description = stringResource(
                        R.string.parameter_value_minutes,
                        uiState.sleepCycleLength
                    ),
                    onClick = { activeDialog = ActiveDialog.CycleLength },
                )
            }
        }
        item {
            SettingsSection(stringResource(R.string.title_contact)) {
                PreferenceRow(
                    icon = Icons.Default.ChatBubble,
                    label = stringResource(R.string.label_send_feedback),
                    description = stringResource(R.string.send_feedback_description),
                    onClick = viewModel::onClickSendFeedback,
                    trailingContent = {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                        )
                    }
                )
            }
        }
        item {
            SettingsSection("About") {
                PreferenceRow(
                    icon = Icons.Default.Code,
                    label = stringResource(R.string.label_github),
                    description = stringResource(R.string.github_description),
                    onClick = viewModel::onClickGithub,
                    trailingContent = {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                        )
                    }
                )
            }
        }
    }
    when (activeDialog) {
        ActiveDialog.Buffer -> SleepBufferDialog(
            onDismiss = { activeDialog = null },
            onConfirm = { minutes ->
                viewModel.onFallAsleepBufferChanged(minutes)
                activeDialog = null
            },
            currentValue = uiState.fallAsleepBuffer
        )

        ActiveDialog.CycleLength -> SleepCycleLengthDialog(
            onDismiss = { activeDialog = null },
            onConfirm = { minutes ->
                viewModel.onSleepCycleLengthChanged(minutes)
                activeDialog = null
            },
            currentValue = uiState.sleepCycleLength
        )

        null -> {}
    }
}