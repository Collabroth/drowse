package com.codebroth.rewake.feature.reminder.ui

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.codebroth.rewake.feature.reminder.notifications.ReminderNotificationService

@Composable
fun ReminderScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val service = ReminderNotificationService(context)
    Column(modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Reminders")
        Button(
            onClick = {service.showNotification()}
        ) {
            Text("Show Notification")
        }

    }
}

@Preview
@Composable
fun ReminderScreenPreview() {
    ReminderScreen()
}