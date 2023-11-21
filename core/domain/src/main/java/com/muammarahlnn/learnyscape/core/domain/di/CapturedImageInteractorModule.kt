package com.muammarahlnn.learnyscape.core.domain.di

import com.muammarahlnn.learnyscape.core.data.repository.CapturedPhotoRepository
import com.muammarahlnn.learnyscape.core.domain.capturedphoto.GetCapturedPhotoUseCase
import com.muammarahlnn.learnyscape.core.domain.capturedphoto.ResetCapturedPhotoUseCase
import com.muammarahlnn.learnyscape.core.domain.capturedphoto.SaveCapturedPhotoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file CapturedImageInteractorModule, 21/11/2023 21.11 by Muammar Ahlan Abimanyu
 */
@Module
@InstallIn(ViewModelComponent::class)
object CapturedImageInteractorModule {

    @Provides
    fun providesGetCapturedPhotoUseCase(
        capturedPhotoRepository: CapturedPhotoRepository
    ): GetCapturedPhotoUseCase = GetCapturedPhotoUseCase(
        capturedPhotoRepository::getPhoto
    )

    @Provides
    fun providesResetCapturedPhotoUseCase(
        capturedPhotoRepository: CapturedPhotoRepository
    ): ResetCapturedPhotoUseCase = ResetCapturedPhotoUseCase(
        capturedPhotoRepository::resetPhoto
    )

    @Provides
    fun providesSaveCapturedPhotoUseCase(
        capturedPhotoRepository: CapturedPhotoRepository
    ): SaveCapturedPhotoUseCase = SaveCapturedPhotoUseCase(
        capturedPhotoRepository::saveCapturedPhoto
    )
}