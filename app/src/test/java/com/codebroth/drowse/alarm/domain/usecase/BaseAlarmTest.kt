package com.codebroth.drowse.alarm.domain.usecase

import com.codebroth.drowse.alarm.data.repository.FakeAlarmRepository
import com.codebroth.drowse.alarm.domain.model.Alarm
import kotlinx.coroutines.runBlocking
import org.junit.Before
import java.time.DayOfWeek

abstract class BaseAlarmTest {
    protected lateinit var fakeAlarmRepository: FakeAlarmRepository

    @Before
    open fun setUp() {
        fakeAlarmRepository = FakeAlarmRepository()
    }

    /**
     * Inserts default alarms into the fake repository.
     * This method creates 24 alarms, one for each hour of the day,
     */
    internal fun insertDefaultAlarms() {
        val alarmsToInsert = mutableListOf<Alarm>()
        (0..23).forEachIndexed { index, hour ->
            alarmsToInsert.add(
                Alarm(
                    id = index,
                    hour = hour,
                    minute = 0,
                    isEnabled = true,
                    daysOfWeek = DayOfWeek.entries.toSet(),
                    label = index.toString(),
                )
            )
        }
        runBlocking { alarmsToInsert.forEach { fakeAlarmRepository.insertAlarm(it) } }
    }
}