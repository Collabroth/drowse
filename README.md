# Drowse

Drowse is an open-source sleep cycle calculator app that helps you optimize your sleep by
calculating the best times to go to bed or wake up. You can schedule alarms directly to your device’s native clock application based on suggestions. Built with the Android Jetpack Compose library.

## Features

* **Sleep Cycle Calculator**: Choose between “Sleep” or “Wake‑Up” modes and get recommendations
  based on 90‑minute sleep cycles.
* **Recommendation Cards**: Tap on a recommendation card schedule your alarm or reminder for the suggested time.
* **Alarm Scheduling**: System alarm hand-off -> using `AlarmClock.ACTION_SET_ALARM` to set alarm on your device’s native clock app.
* **Reminders**: Create one-off or repeating bedtime reminders to ensure you are consistent with your sleep.
* **Persistence**:
    * **Room Database** for storing reminder data.
    * **DataStore** for saving user preferences.
    * **Broadcast Receivers** to reschedule reminders on boot.

## 💻 Usage

1. **Calculator Tab**
    * Switch between **Sleep‑At** (choose bedtime) or **Wake‑Up** (choose wake up time) modes
    * Select a time using the time picker.
    * View the suggested times.
    * Tap a recommendation card to schedule the alarm or reminder.
2. **Reminders Tab**
    * Manage your reminders.
    * Reminders can be scheduled as a one time reminder (non-repeating) or repeating reminders where you can select specific days.
3. **Settings**
    * Toggle 24-hour format.
    * Theme preferences (Dark/Light/Dynamic).
    * Customize sleep buffer (time it takes to fall asleep) and sleep cycle length.

## 📄 License

Drowse is licensed under the Apache License, Version 2.0. See [LICENSE](LICENSE) for details.
