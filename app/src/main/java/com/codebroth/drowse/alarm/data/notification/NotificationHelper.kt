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

package com.codebroth.drowse.alarm.data.notification

import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import com.codebroth.drowse.R
import com.codebroth.drowse.alarm.data.AlarmConstants.CHANNEL_ID
import com.codebroth.drowse.alarm.data.AlarmConstants.INTENT_ACTION_DISMISS

/**
 * Helper for building notifications.
 */
object NotificationHelper {
    /**
     * Builds a full screen notification for an alarm.
     *
     * Might show up as a heads up notification if user is actively
     * using the phone.
     */
    fun buildAlarmNotification(
        context: Context,
        fullScreenPendingIntent: PendingIntent,
        dismissPendingIntent: PendingIntent
    ) = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.baseline_alarm_24)
        .setContentTitle(context.getString(R.string.title_alarm))
        .setContentText(context.getString(R.string.notification_text_swipe_to_dismiss))
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setCategory(NotificationCompat.CATEGORY_ALARM)
        .setOngoing(true)
        .setFullScreenIntent(fullScreenPendingIntent, true)
        .addAction(R.drawable.baseline_alarm_24, INTENT_ACTION_DISMISS, dismissPendingIntent)
        .setDeleteIntent(dismissPendingIntent)
        .build()
}