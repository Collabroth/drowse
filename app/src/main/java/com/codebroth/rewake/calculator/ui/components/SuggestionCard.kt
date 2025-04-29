package com.codebroth.rewake.calculator.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codebroth.rewake.calculator.domain.model.SleepRecommendation
import com.codebroth.rewake.core.domain.util.TimeUtils
import java.time.LocalTime

@Composable
fun SuggestionCard(
    rec: SleepRecommendation,
    modifier: Modifier = Modifier,
    onSchedule: (LocalTime) -> Unit
) {
    Card(
        modifier = modifier
            .padding(4.dp),
        elevation = CardDefaults.elevatedCardElevation(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = TimeUtils.formatTime(rec.time),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${rec.hours} hours",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "${rec.cycles} cycles",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            IconButton(
                onClick = { onSchedule(rec.time) }
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "schedule reminder"
                )
            }
        }
    }
}