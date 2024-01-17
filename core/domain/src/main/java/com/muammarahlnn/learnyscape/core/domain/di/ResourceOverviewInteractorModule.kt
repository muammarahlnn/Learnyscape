package com.muammarahlnn.learnyscape.core.domain.di

import com.muammarahlnn.learnyscape.core.data.repository.ResourceOverviewRepository
import com.muammarahlnn.learnyscape.core.domain.resourceoverview.GetAnnouncementsUseCase
import com.muammarahlnn.learnyscape.core.domain.resourceoverview.GetAssignmentsUseCase
import com.muammarahlnn.learnyscape.core.domain.resourceoverview.GetModulesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceOverviewInteractorModule, 17/01/2024 15.22
 */
@Module
@InstallIn(ViewModelComponent::class)
object ResourceOverviewInteractorModule {

    @ViewModelScoped
    @Provides
    fun providesGetAnnouncementsUseCase(
        resourceOverviewRepository: ResourceOverviewRepository
    ): GetAnnouncementsUseCase = GetAnnouncementsUseCase(
        resourceOverviewRepository::getAnnouncements
    )

    @ViewModelScoped
    @Provides
    fun providesGetModulesUseCase(
        resourceOverviewRepository: ResourceOverviewRepository
    ): GetModulesUseCase = GetModulesUseCase(
        resourceOverviewRepository::getModules
    )

    @ViewModelScoped
    @Provides
    fun providesGetAssignmentsUseCase(
        resourceOverviewRepository: ResourceOverviewRepository
    ): GetAssignmentsUseCase = GetAssignmentsUseCase(
        resourceOverviewRepository::getAssignments
    )
}