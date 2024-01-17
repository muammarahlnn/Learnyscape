package com.muammarahlnn.learnyscape.core.network.di

import com.muammarahlnn.learnyscape.core.network.datasource.AvailableClassNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.HomeNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.JoinRequestNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.LoginNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.ProfileNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.ResourceCreateNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.ResourceOverviewNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.SchedulesNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.impl.AvailableClassNetworkDataSourceImpl
import com.muammarahlnn.learnyscape.core.network.datasource.impl.HomeNetworkDataSourceImpl
import com.muammarahlnn.learnyscape.core.network.datasource.impl.JoinRequestNetworkDataSourceImpl
import com.muammarahlnn.learnyscape.core.network.datasource.impl.LoginNetworkDataSourceImpl
import com.muammarahlnn.learnyscape.core.network.datasource.impl.ProfileNetworkDataSourceImpl
import com.muammarahlnn.learnyscape.core.network.datasource.impl.ResourceCreateNetworkDataSourceImpl
import com.muammarahlnn.learnyscape.core.network.datasource.impl.ResourceOverviewNetworkDataSourceImpl
import com.muammarahlnn.learnyscape.core.network.datasource.impl.SchedulesNetworkDataSourceImpl
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

    @Binds
    fun bindsHomeNetworkDataSource(
        homeNetworkDataSource: HomeNetworkDataSourceImpl
    ): HomeNetworkDataSource

    @Binds
    fun bindsAvailableClassNetworkDataSource(
        availableClassNetworkDataSource: AvailableClassNetworkDataSourceImpl
    ): AvailableClassNetworkDataSource

    @Binds
    fun bindsProfileNetworkDataSource(
        profileNetworkDataSource: ProfileNetworkDataSourceImpl
    ): ProfileNetworkDataSource

    @Binds
    fun bindsScheduleNetworkDataSource(
        schedulesNetworkDataSource: SchedulesNetworkDataSourceImpl
    ): SchedulesNetworkDataSource

    @Binds
    fun bindsResourceCreateNetworkDataSource(
        resourceCreateNetworkDataSource: ResourceCreateNetworkDataSourceImpl
    ): ResourceCreateNetworkDataSource

    @Binds
    fun bindsResourceOverviewNetworkDataSource(
        resourceOverviewNetworkDataSource: ResourceOverviewNetworkDataSourceImpl
    ): ResourceOverviewNetworkDataSource

    @Binds
    fun bindsJoinRequestNetworkDataSource(
        joinRequestNetworkDataSource: JoinRequestNetworkDataSourceImpl
    ): JoinRequestNetworkDataSource
}