package com.muammarahlnn.learnyscape.core.domain.di

import com.muammarahlnn.learnyscape.core.data.repository.SchedulesRepository
import com.muammarahlnn.learnyscape.core.domain.schedules.GetSchedulesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SchedulesInteractorModule, 08/12/2023 22.56
 */
@Module
@InstallIn(ViewModelComponent::class)
object SchedulesInteractorModule {

    @ViewModelScoped
    @Provides
    fun providesGetSchedulesUseCase(
        schedulesRepository: SchedulesRepository
    ): GetSchedulesUseCase = GetSchedulesUseCase(
        schedulesRepository::getSchedules
    )
}