package com.muammarahlnn.learnyscape.core.domain.di

import com.muammarahlnn.learnyscape.core.data.repository.FileRepository
import com.muammarahlnn.learnyscape.core.domain.file.SaveImageToFileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file FileInteractorModule, 24/11/2023 18.52 by Muammar Ahlan Abimanyu
 */
@Module
@InstallIn(ViewModelComponent::class)
object FileInteractorModule {

    @ViewModelScoped
    @Provides
    fun providesSaveImageToFileUseCase(
        fileRepository: FileRepository
    ): SaveImageToFileUseCase = SaveImageToFileUseCase(
        fileRepository::saveImageToFile
    )
}