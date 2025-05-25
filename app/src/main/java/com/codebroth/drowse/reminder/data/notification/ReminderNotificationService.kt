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

package com.codebroth.drowse.reminder.data.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.codebroth.drowse.MainActivity
import com.codebroth.drowse.R
import com.codebroth.drowse.reminder.data.Constants.REMINDER_NOTIFICATION_CHANNEL_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ReminderNotificationService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationManager: NotificationManager
) {

    val activityIntent = Intent(context, MainActivity::class.java)
    val activityPendingIntent: PendingIntent = PendingIntent.getActivity(
        context,
        0,
        activityIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    PendingIntent.FLAG_IMMUTABLE
                else 0
    )

    fun showReminderNotification(reminderId: Int) {
        val notification = NotificationCompat.Builder(context, REMINDER_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle(context.getString(R.string.reminder_notification_title))
            .setContentText(context.getString(R.string.reminder_notification_description))
            .setContentIntent(activityPendingIntent)
            .build()

        notificationManager.notify(
            reminderId,
            notification
        )
    }

    companion object
}