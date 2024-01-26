package com.muammarahlnn.learnyscape.core.domain.di

import com.muammarahlnn.learnyscape.core.data.repository.ProfileRepository
import com.muammarahlnn.learnyscape.core.domain.profile.GetProfilePicByUrlUeCase
import com.muammarahlnn.learnyscape.core.domain.profile.GetProfilePicUseCase
import com.muammarahlnn.learnyscape.core.domain.profile.LogoutUseCase
import com.muammarahlnn.learnyscape.core.domain.profile.UploadProfilePicUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


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

    @Provides
    fun providesGetProfilePicUseCase(
        profileRepository: ProfileRepository
    ): GetProfilePicUseCase = GetProfilePicUseCase(
        profileRepository::getProfilePic
    )

    @Provides
    @ViewModelScoped
    fun providesGetProfilePicByUrlUseCase(
        profileRepository: ProfileRepository
    ): GetProfilePicByUrlUeCase = GetProfilePicByUrlUeCase(
        profileRepository::getProfilePicByUrl
    )
}