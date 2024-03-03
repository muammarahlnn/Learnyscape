package com.muammarahlnn.learnyscape.core.domain.di

import com.muammarahlnn.learnyscape.core.data.repository.PendingRequestRepository
import com.muammarahlnn.learnyscape.core.domain.pendingrequest.CancelStudentRequestClassUseCase
import com.muammarahlnn.learnyscape.core.domain.pendingrequest.GetStudentPendingRequestClassesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * @Author Muammar Ahlan Abimanyu
 * @File PendingRequestInteractorModule, 29/02/2024 22.21
 */
@Module
@InstallIn(ViewModelComponent::class)
object PendingRequestInteractorModule {

    @Provides
    @ViewModelScoped
    fun providesGetStudentPendingRequestClassesUseCase(
        pendingRequestRepository: PendingRequestRepository
    ): GetStudentPendingRequestClassesUseCase = GetStudentPendingRequestClassesUseCase(
        pendingRequestRepository::getStudentPendingRequestClasses
    )

    @Provides
    @ViewModelScoped
    fun providesCancelStudentRequestClassUseCase(
        pendingRequestRepository: PendingRequestRepository
    ): CancelStudentRequestClassUseCase = CancelStudentRequestClassUseCase(
        pendingRequestRepository::cancelStudentRequestClass
    )
}