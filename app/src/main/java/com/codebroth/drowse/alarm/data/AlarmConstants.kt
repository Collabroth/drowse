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

package com.codebroth.drowse.alarm.data

object AlarmConstants {
    /**
     * Unique ID for the alarm notification channel.
     */
    const val CHANNEL_ID = "alarm_channel"

    /**
     * Name of the alarm notification channel.
     */
    const val CHANNEL_NAME = "Alarm"

    /**
     * Name of ID extra data.
     */
    const val EXTRA_ALARM_ID = "extra_alarm_id"

    /**
     * Name of Alarm Time extra data.
     */
    const val EXTRA_ALARM_TIME = "extra_alarm_time"

    /**
     * Name of Alarm Label extra data.
     */
    const val EXTRA_ALARM_LABEL = "extra_alarm_label"

    /**
     * Name of the action to dismiss the alarm.
     */
    const val INTENT_ACTION_DISMISS = "Dismiss"
}