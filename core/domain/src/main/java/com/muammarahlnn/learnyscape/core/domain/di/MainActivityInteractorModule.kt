package com.muammarahlnn.learnyscape.core.domain.di

import com.muammarahlnn.learnyscape.core.data.repository.HomeRepository
import com.muammarahlnn.learnyscape.core.domain.home.GetLoggedInUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file MainActivityInteractorModule, 19/11/2023 04.20 by Muammar Ahlan Abimanyu
 */
@Module
@InstallIn(ActivityComponent::class)
object MainActivityInteractorModule {

    @Provides
    fun providesGetLoggedInUserUseCase(
        homeRepository: HomeRepository
    ): GetLoggedInUserUseCase = GetLoggedInUserUseCase(
        homeRepository::getLoggedInUser
    )
}