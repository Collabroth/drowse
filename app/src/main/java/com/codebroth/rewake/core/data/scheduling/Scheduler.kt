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

package com.codebroth.rewake.core.data.scheduling

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.DayOfWeek
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Scheduler @Inject constructor(
    @ApplicationContext private val context: Context,
    private val alarmManager: AlarmManager
) {
    @SuppressLint("MissingPermission")
    fun scheduleAt(
        id: Int,
        triggerAtMillis: Long,
        receiver: Class<*>,
        prepare: Intent.() -> Unit = {}
    ) {
        val intent = Intent(context, receiver).apply { prepare() }
        val pendingIntent = createPendingIntent(id, intent)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            pendingIntent
        )
    }

    fun cancel(id: Int, receiver: Class<*>) {
        DayOfWeek.entries.forEach { day ->
            val requestCode = id * 10 + (day.ordinal)
            val intent = Intent(context, receiver)
            val pendingIntent = createPendingIntent(requestCode, intent)

            alarmManager.cancel(pendingIntent)
        }
    }

    private fun createPendingIntent(
        requestCode: Int,
        intent: Intent
    ): PendingIntent = PendingIntent.getBroadcast(
        context,
        requestCode,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    companion object {
        const val EXTRA_ONE_SHOT = "extra_one_shot"
    }
}