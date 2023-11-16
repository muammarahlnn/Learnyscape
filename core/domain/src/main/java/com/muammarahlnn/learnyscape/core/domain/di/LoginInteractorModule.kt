package com.muammarahlnn.learnyscape.core.domain.di

import com.muammarahlnn.learnyscape.core.data.repository.LoginRepository
import com.muammarahlnn.learnyscape.core.domain.login.IsUserLoggedInUseCase
import com.muammarahlnn.learnyscape.core.domain.login.PostLoginUserUseCase
import com.muammarahlnn.learnyscape.core.domain.login.SaveUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file LoginInteractorModule, 16/11/2023 19.26 by Muammar Ahlan Abimanyu
 */
@Module
@InstallIn(ViewModelComponent::class)
object LoginInteractorModule {

    @Provides
    fun providesIsUserLoggedInUseCase(
        loginRepository: LoginRepository
    ): IsUserLoggedInUseCase = IsUserLoggedInUseCase(
        loginRepository::isUserLoggedIn
    )

    @Provides
    fun providesPostLoginUserUseCase(
        loginRepository: LoginRepository
    ): PostLoginUserUseCase = PostLoginUserUseCase(
        loginRepository::postLoginUser
    )

    @Provides
    fun providesSaveUserUseCase(
        loginRepository: LoginRepository
    ): SaveUserUseCase = SaveUserUseCase(
        loginRepository::saveUser
    )
}