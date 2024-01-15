package com.muammarahlnn.learnyscape.core.domain.di

import com.muammarahlnn.learnyscape.core.data.repository.ResourceCreateRepository
import com.muammarahlnn.learnyscape.core.domain.resourcecreate.CreateAnnouncementUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceCreateInteractorModule, 13/01/2024 05.36
 */
@Module
@InstallIn(ViewModelComponent::class)
object ResourceCreateInteractorModule {
    
    @ViewModelScoped
    @Provides
    fun providesCreateAnnouncementUseCase(
        resourceCreateRepository: ResourceCreateRepository
    ): CreateAnnouncementUseCase = CreateAnnouncementUseCase(
        resourceCreateRepository::createAnnouncement
    )
}