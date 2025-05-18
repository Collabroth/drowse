package com.codebroth.rewake.alarm.domain.usecase

import com.codebroth.rewake.alarm.data.repository.AlarmRepository
import com.codebroth.rewake.alarm.domain.model.Alarm
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetAllAlarmsUseCase @Inject constructor(
    private val repo: AlarmRepository
) {
    operator fun invoke(): Flow<List<Alarm>> =
        repo.getAllAlarms()
}