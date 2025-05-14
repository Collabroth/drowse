package com.codebroth.rewake.calculator.di

import com.codebroth.rewake.calculator.domain.usecase.CalculateBedTimesUseCase
import com.codebroth.rewake.calculator.domain.usecase.CalculateWakeTimesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CalculatorUseCaseModule {

    @Provides
    @Singleton
    fun provideCalculateWakeTimesUseCase(): CalculateWakeTimesUseCase {
        return CalculateWakeTimesUseCase()
    }

    @Provides
    @Singleton
    fun provideCalculateBedTimesUseCase(): CalculateBedTimesUseCase {
        return CalculateBedTimesUseCase()
    }
}