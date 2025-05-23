package com.codebroth.rewake.alarm.data

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