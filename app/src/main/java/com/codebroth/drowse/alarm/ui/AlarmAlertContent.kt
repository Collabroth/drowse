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

package com.codebroth.drowse.alarm.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.codebroth.drowse.R
import com.codebroth.drowse.core.domain.util.TimeUtils
import java.time.LocalTime

/**
 * Alarm alert screen content.
 *
 * @param alarmTime The time of the alarm.
 * @param alarmLabel The label of the alarm.
 * @param viewModel The [AlarmAlertViewModel] instance.
 */
@Composable
fun AlarmAlertContent(
    alarmTime: LocalTime,
    alarmLabel: String?,
    modifier: Modifier = Modifier,
    viewModel: AlarmAlertViewModel = hiltViewModel()
) {

    val is24Hour by viewModel.is24HourFormat.collectAsState()

    val pulse = rememberInfiniteTransition()
        .animateFloat(
            initialValue = 0.9f,
            targetValue = 1.1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 800,
                    easing = FastOutSlowInEasing,
                ),
                repeatMode = RepeatMode.Reverse
            )
        )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                horizontal = dimensionResource(R.dimen.padding_large),
                vertical = dimensionResource(R.dimen.padding_xlarge)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {
            Icon(
                imageVector = Icons.Default.Alarm,
                contentDescription = stringResource(R.string.alarm_icon_content_description),
                modifier = Modifier
                    .size(dimensionResource(R.dimen.alarm_alert_icon_size))
                    .scale(pulse.value),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = TimeUtils.formatTime(alarmTime, is24Hour),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
            if (alarmLabel != null) {
                Text(
                    text = alarmLabel,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        ) {
            Button(
                onClick = { viewModel.onDismiss() },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Text(stringResource(R.string.action_dismiss))
            }
            OutlinedButton(
                onClick = { viewModel.onSnooze() },
                modifier = Modifier.weight(1f),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Text(stringResource(R.string.action_snooze))
            }
        }
    }
}