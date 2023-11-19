package com.muammarahlnn.learnyscape.core.domain.di

import com.muammarahlnn.learnyscape.core.data.repository.HomeRepository
import com.muammarahlnn.learnyscape.core.domain.home.GetEnrolledClassesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeInteractorModule, 16/11/2023 18.18 by Muammar Ahlan Abimanyu
 */
@Module
@InstallIn(ViewModelComponent::class)
object HomeInteractorModule {

    @Provides
    fun providesGetEnrolledClassesUseCase(
        homeRepository: HomeRepository
    ): GetEnrolledClassesUseCase = GetEnrolledClassesUseCase(
        homeRepository::getEnrolledClasses
    )
}