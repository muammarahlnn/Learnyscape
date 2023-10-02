package com.muammarahlnn.learnyscape.core.network.di

import com.muammarahlnn.learnyscape.core.network.datasource.LoginNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.impl.LoginNetworkDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file NetworkDataSourceModule, 02/10/2023 15.57 by Muammar Ahlan Abimanyu
 */

@Module
@InstallIn(SingletonComponent::class)
interface NetworkDataSourceModule {

    @Binds
    fun bindsLoginNetworkDataSource(
        loginNetworkDataSource: LoginNetworkDataSourceImpl
    ): LoginNetworkDataSource
}