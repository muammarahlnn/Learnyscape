package com.muammarahlnn.learnyscape.core.domain.di

import com.muammarahlnn.learnyscape.core.data.repository.AvailableClassRepository
import com.muammarahlnn.learnyscape.core.domain.availableclass.GetAvailableClassesUseCase
import com.muammarahlnn.learnyscape.core.domain.availableclass.RequestJoinClassUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file AvailableClassInteractorModule, 16/11/2023 20.50 by Muammar Ahlan Abimanyu
 */
@Module
@InstallIn(ViewModelComponent::class)
object AvailableClassInteractorModule {

    @Provides
    fun providesGetAvailableClassesUseCase(
        availableClassRepository: AvailableClassRepository
    ): GetAvailableClassesUseCase = GetAvailableClassesUseCase(
        availableClassRepository::getAvailableClasses
    )

    @Provides
    fun providesRequestJoinClassUseCase(
        availableClassRepository: AvailableClassRepository
    ): RequestJoinClassUseCase = RequestJoinClassUseCase(
        availableClassRepository::requestJoinClass
    )
}