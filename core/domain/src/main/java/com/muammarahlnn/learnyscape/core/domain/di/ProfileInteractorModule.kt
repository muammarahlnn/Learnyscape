package com.muammarahlnn.learnyscape.core.domain.di

import com.muammarahlnn.learnyscape.core.data.repository.ProfileRepository
import com.muammarahlnn.learnyscape.core.domain.profile.LogoutUseCase
import com.muammarahlnn.learnyscape.core.domain.profile.UploadProfilePicUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ProfileInteractorModule, 16/11/2023 19.44 by Muammar Ahlan Abimanyu
 */
@Module
@InstallIn(ViewModelComponent::class)
object ProfileInteractorModule {

    @Provides
    fun providesLogoutUseCase(
        profileRepository: ProfileRepository
    ): LogoutUseCase = LogoutUseCase(
        profileRepository::logout
    )

    @Provides
    fun providesUploadProfilePicUseCase(
        profileRepository: ProfileRepository
    ): UploadProfilePicUseCase = UploadProfilePicUseCase(
        profileRepository::uploadProfilePic
    )
}