package com.muammarahlnn.learnyscape.core.data.di

import com.muammarahlnn.learnyscape.core.data.repository.LoginRepository
import com.muammarahlnn.learnyscape.core.data.repository.UserRepository
import com.muammarahlnn.learnyscape.core.data.repository.impl.LoginRepositoryImpl
import com.muammarahlnn.learnyscape.core.data.repository.impl.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


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
    fun bindsUserRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository
}