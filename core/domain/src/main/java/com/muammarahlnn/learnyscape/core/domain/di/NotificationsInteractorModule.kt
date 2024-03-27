package com.muammarahlnn.learnyscape.core.domain.di

import com.muammarahlnn.learnyscape.core.data.repository.NotificationsRepository
import com.muammarahlnn.learnyscape.core.domain.notifications.GetNotificationsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * @Author Muammar Ahlan Abimanyu
 * @File NoticiationsInteractorModule, 27/03/2024 01.33
 */
@Module
@InstallIn(ViewModelComponent::class)
object NotificationsInteractorModule {

    @Provides
    @ViewModelScoped
    fun providesGetNotificationsUseCase(
        notificationsRepository: NotificationsRepository
    ): GetNotificationsUseCase = GetNotificationsUseCase(
        notificationsRepository::getNotifications
    )
}