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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.codebroth.drowse.calculator.domain.model.SleepRecommendation
import com.codebroth.drowse.core.domain.util.TimeUtils
import com.codebroth.drowse.core.ui.theme.DrowseTheme
import java.time.LocalTime

@Composable
fun RecommendationCard(
    rec: SleepRecommendation,
    onClick: (LocalTime) -> Unit,
    modifier: Modifier = Modifier,
    is24Hour: Boolean = false,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.card_elevation)),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(rec.time) },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box {
                Text(
                    text = TimeUtils.formatTime(rec.time, is24Hour),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = stringResource(
                            R.string.label_abbreviated_hour,
                            rec.minutes / 60,
                            rec.minutes % 60
                        ),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = stringResource(R.string.label_cycles, rec.cycles),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(Modifier.width(dimensionResource(R.dimen.spacer_small)))
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = stringResource(R.string.label_schedule)
                )
            }
        }
    }
}

@Preview
@Composable
fun RecommendationCardPreview() {
    DrowseTheme {
        RecommendationCard(
            rec = SleepRecommendation(
                time = LocalTime.of(22, 30),
                minutes = 7,
                cycles = 5
            ),
            onClick = {},
            is24Hour = true
        )
    }
}

@Preview
@Composable
fun RecommendationCardDarkThemePreview() {
    DrowseTheme(darkTheme = true) {
        RecommendationCard(
            rec = SleepRecommendation(
                time = LocalTime.of(22, 30),
                minutes = 7,
                cycles = 5
            ),
            onClick = {},
            is24Hour = true
        )
    }
}


