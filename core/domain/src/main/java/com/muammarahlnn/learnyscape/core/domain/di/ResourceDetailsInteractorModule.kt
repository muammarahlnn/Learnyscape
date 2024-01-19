package com.muammarahlnn.learnyscape.core.domain.di

import com.muammarahlnn.learnyscape.core.data.repository.ResourceDetailsRepository
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.GetModuleDetailsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceDetailsInteractorModule, 18/01/2024 17.50
 */
@Module
@InstallIn(ViewModelComponent::class)
object ResourceDetailsInteractorModule {

    @ViewModelScoped
    @Provides
    fun providesGetModuleDetailsUseCase(
        resourceDetailsRepository: ResourceDetailsRepository
    ): GetModuleDetailsUseCase = GetModuleDetailsUseCase(
        resourceDetailsRepository::getModuleDetails
    )
}