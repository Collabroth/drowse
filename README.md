# Drowse

Drowse is an open-source sleep cycle calculator app that helps you optimize your sleep by calculating the best times to go to bed or wake up. You can schedule alarms directly in-app or hand off to your device’s native clock application. Built with the Android Jetpack Compose library.
---

## Features

* **Sleep Cycle Calculator**: Choose between “Sleep” or “Wake‑Up” modes and get recommendations based on 90‑minute sleep cycles.
* **Recommendation Cards**: One-tap cards schedule your alarm for the suggested time.
* **Pick where alarms are schedules**:

  * **In-App Alarms** via `AlarmManager` for full control and customization.
  * **System Alarm Hand-Off** using `AlarmClock.ACTION_SET_ALARM` when you prefer your device’s native clock app (toggle in Settings).
* **Reminders**: Create one-off or repeating reminders with their own UI.
* **Full-Screen Alarm UI**: Custom full-screen alarm activity with snooze and dismiss, when the phone is locked.
* **Persistence**:

  * **Room** for alarm and reminder data
  * **DataStore** for user preferences
  * **Broadcast Receivers** to reschedule alarms on boot

---

## 💻 Usage

1. **Calculator Tab**

   * Switch between **Sleep‑At** (choose bedtime) or **Wake‑Up** modes
   * select a time.
   * Tap a recommendation card to schedule the alarm or reminder.
2. **Alarms Tab** (hidden if system-alarm toggle is on)

   * View your in-app alarms; edit or delete them
3. **Reminders Tab**

   * Manage your reminders.
4. **Settings**

   * Toggle **24-hour format**
   * Toggle **Use system alarm app**

---

## 📄 License

Drowse is licensed under the Apache License, Version 2.0. See [LICENSE](LICENSE) for details.
