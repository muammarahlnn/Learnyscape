package com.muammarahlnn.learnyscape.core.data.di

import com.muammarahlnn.learnyscape.core.data.repository.AvailableClassRepository
import com.muammarahlnn.learnyscape.core.data.repository.CapturedPhotoRepository
import com.muammarahlnn.learnyscape.core.data.repository.FileRepository
import com.muammarahlnn.learnyscape.core.data.repository.HomeRepository
import com.muammarahlnn.learnyscape.core.data.repository.LoginRepository
import com.muammarahlnn.learnyscape.core.data.repository.ProfileRepository
import com.muammarahlnn.learnyscape.core.data.repository.impl.AvailableClassRepositoryImpl
import com.muammarahlnn.learnyscape.core.data.repository.impl.CapturedPhotoRepositoryImpl
import com.muammarahlnn.learnyscape.core.data.repository.impl.FileRepositoryImpl
import com.muammarahlnn.learnyscape.core.data.repository.impl.HomeRepositoryImpl
import com.muammarahlnn.learnyscape.core.data.repository.impl.LoginRepositoryImpl
import com.muammarahlnn.learnyscape.core.data.repository.impl.ProfileRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file DataModule, 02/10/2023 02.31 by Muammar Ahlan Abimanyu
 */

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsLoginRepository(
        loginRepository: LoginRepositoryImpl
    ): LoginRepository

    @Binds
    fun bindsProfileRepository(
        profileRepository: ProfileRepositoryImpl
    ): ProfileRepository

    @Binds
    fun bindsHomeRepository(
        homeRepository: HomeRepositoryImpl
    ): HomeRepository

    @Binds
    fun bindsAvailableClassRepository(
        availableClassRepository: AvailableClassRepositoryImpl
    ): AvailableClassRepository

    @Singleton
    @Binds
    fun bindsCapturedPhotoRepository(
        capturedPhotoRepository: CapturedPhotoRepositoryImpl
    ): CapturedPhotoRepository

    @Binds
    fun bindsFileRepository(
        fileRepository: FileRepositoryImpl
    ): FileRepository
}