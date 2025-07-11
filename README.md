<div align="center">

<img width="120" alt="ic_launcher-playstore" src="https://github.com/user-attachments/assets/af9981ff-a02c-410a-bbe7-d5bff5ab21c5" />

# Drowse - Sleep Calculator

Drowse is an open-source sleep cycle calculator app that helps you optimize your sleep by
calculating the best times to go to bed or wake up. You can schedule alarms directly to your device’s native clock application based on suggestions. Built with the Android Jetpack Compose library.

## Download

[![PlayStore](https://img.shields.io/endpoint?color=green&logo=google-play&logoColor=green&url=https%3A%2F%2Fplay.cuzi.workers.dev%2Fplay%3Fi%3Dcom.codebroth.drowse%26gl%3DUS%26hl%3Den%26l%3DDrowse%26m%3D%24installs)](https://play.google.com/store/apps/details?id=com.codebroth.drowse)

*Requires Android 9 or above.*

</div>
<div align="left">

<div align="center">
  
## Features

</div>

* **Sleep Cycle Calculator**: Choose between “Sleep” or “Wake‑Up” modes and get recommendations
  based on 90‑minute sleep cycles.
* **Recommendation Cards**: Tap on a recommendation card schedule your alarm or reminder for the suggested time.
* **Alarm Scheduling**: System alarm hand-off -> using `AlarmClock.ACTION_SET_ALARM` to set alarm on your device’s native clock app.
* **Reminders**: Create one-off or repeating bedtime reminders to ensure you are consistent with your sleep.
* **Persistence**:
    * **Room Database** for storing reminder data.
    * **DataStore** for saving user preferences.
    * **Broadcast Receivers** to reschedule reminders on boot.

<div align="center">
  
## 💻 Usage

</div>

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
<div align="center">

## 📄 License

<pre>
Copyright © 2025 Jayman Rana

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
</pre>

Drowse is licensed under the Apache License, Version 2.0. See [LICENSE](LICENSE) for details.
</
</div>
