package com.codebroth.rewake.alarm.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codebroth.rewake.alarm.domain.model.Alarm
import com.codebroth.rewake.core.domain.util.toDayOfWeekSet

@Entity(tableName = "alarms")
data class AlarmEntity(
    val hour: Int,
    val minute: Int,
    val daysOfWeekBitmask: Int,
    val label: String? = null,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val isEnabled: Boolean = true,
)

fun AlarmEntity.toDomainModel(): Alarm =
    Alarm(
        id = id,
        hour = hour,
        minute = minute,
        daysOfWeek = daysOfWeekBitmask.toDayOfWeekSet(),
        label = label,
        isEnabled = isEnabled
    )