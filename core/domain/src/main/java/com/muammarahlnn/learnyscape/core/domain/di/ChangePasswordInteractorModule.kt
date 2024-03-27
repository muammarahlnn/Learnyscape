package com.muammarahlnn.learnyscape.core.domain.di

import com.muammarahlnn.learnyscape.core.data.repository.ChangePasswordRepository
import com.muammarahlnn.learnyscape.core.domain.changepassword.ChangePasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ChangePasswordInteractorModule, 27/03/2024 15.43
 */
@Module
@InstallIn(ViewModelComponent::class)
object ChangePasswordInteractorModule {

    @Provides
    @ViewModelScoped
    fun providesChangePasswordUseCase(
        changePasswordRepository: ChangePasswordRepository
    ): ChangePasswordUseCase = ChangePasswordUseCase(
        changePasswordRepository::changePassword
    )
}