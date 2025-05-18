package com.codebroth.rewake.alarm.domain.usecase

import com.codebroth.rewake.alarm.data.repository.AlarmRepository
import com.codebroth.rewake.alarm.domain.model.Alarm
import jakarta.inject.Inject

class UpdateAlarmUseCase @Inject constructor(
    private val repo: AlarmRepository
) {
    suspend operator fun invoke(alarm: Alarm) {
        repo.updateAlarm(alarm)
    }
}