package com.muammarahlnn.learnyscape.core.domain.di

import com.muammarahlnn.learnyscape.core.data.repository.ClassFeedRepository
import com.muammarahlnn.learnyscape.core.domain.classfeed.GetClassFeedsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassFeedInteractorModule, 27/01/2024 12.30
 */
@Module
@InstallIn(ViewModelComponent::class)
object ClassFeedInteractorModule {

    @Provides
    @ViewModelScoped
    fun providesGetClassFeedsUseCase(
        classFeedRepository: ClassFeedRepository
    ): GetClassFeedsUseCase = GetClassFeedsUseCase(
        classFeedRepository::getClassFeeds
    )
}