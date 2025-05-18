package com.codebroth.rewake.alarm.di

import com.codebroth.rewake.alarm.data.repository.AlarmRepository
import com.codebroth.rewake.alarm.data.repository.AlarmRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AlarmRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAlarmRepository(
        alarmRepositoryImpl: AlarmRepositoryImpl
    ): AlarmRepository
}