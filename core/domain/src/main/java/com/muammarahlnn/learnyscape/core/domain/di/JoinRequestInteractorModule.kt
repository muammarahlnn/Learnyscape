package com.muammarahlnn.learnyscape.core.domain.di

import com.muammarahlnn.learnyscape.core.data.repository.JoinRequestRepository
import com.muammarahlnn.learnyscape.core.domain.joinrequest.GetWaitingClassUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * @Author Muammar Ahlan Abimanyu
 * @File JoinRequestInteractorModule, 17/01/2024 21.58
 */
@Module
@InstallIn(ViewModelComponent::class)
object JoinRequestInteractorModule {

    @ViewModelScoped
    @Provides
    fun providesGetWaitingClassUseCase(
        joinRequestRepository: JoinRequestRepository
    ): GetWaitingClassUseCase = GetWaitingClassUseCase(
        joinRequestRepository::getWaitingListClass
    )
}