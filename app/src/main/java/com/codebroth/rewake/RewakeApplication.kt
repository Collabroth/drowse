package com.codebroth.rewake

import android.app.Application
import android.os.Build
import com.codebroth.rewake.reminder.notifications.ReminderNotificationChannel.createReminderNotificationChannel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RewakeApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createReminderNotificationChannel(applicationContext)
        }

    }
}