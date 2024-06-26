package com.muammarahlnn.learnyscape.core.network.di

import com.muammarahlnn.learnyscape.core.network.datasource.AttachmentNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.AvailableClassNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.ChangePasswordNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.ClassFeedNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.ClassMembersNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.HomeNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.JoinRequestNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.LoginNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.NotificationsNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.PendingRequestNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.ProfileNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.QuizSessionNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.ResourceCreateNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.ResourceDetailsNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.ResourceOverviewNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.SchedulesNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.SubmissionDetailsNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.impl.AttachmentNetworkDataSourceImpl
import com.muammarahlnn.learnyscape.core.network.datasource.impl.AvailableClassNetworkDataSourceImpl
import com.muammarahlnn.learnyscape.core.network.datasource.impl.ChangePasswordNetworkDataSourceImpl
import com.muammarahlnn.learnyscape.core.network.datasource.impl.ClassFeedNetworkDataSourceImpl
import com.muammarahlnn.learnyscape.core.network.datasource.impl.ClassMembersNetworkDataSourceImpl
import com.muammarahlnn.learnyscape.core.network.datasource.impl.HomeNetworkDataSourceImpl
import com.muammarahlnn.learnyscape.core.network.datasource.impl.JoinRequestNetworkDataSourceImpl
import com.muammarahlnn.learnyscape.core.network.datasource.impl.LoginNetworkDataSourceImpl
import com.muammarahlnn.learnyscape.core.network.datasource.impl.NotificationsNetworkDataSourceImpl
import com.muammarahlnn.learnyscape.core.network.datasource.impl.PendingRequestNetworkDataSourceImpl
import com.muammarahlnn.learnyscape.core.network.datasource.impl.ProfileNetworkDataSourceImpl
import com.muammarahlnn.learnyscape.core.network.datasource.impl.QuizSessionNetworkDataSourceImpl
import com.muammarahlnn.learnyscape.core.network.datasource.impl.ResourceCreateNetworkDataSourceImpl
import com.muammarahlnn.learnyscape.core.network.datasource.impl.ResourceDetailsNetworkDataSourceImpl
import com.muammarahlnn.learnyscape.core.network.datasource.impl.ResourceOverviewNetworkDataSourceImpl
import com.muammarahlnn.learnyscape.core.network.datasource.impl.SchedulesNetworkDataSourceImpl
import com.muammarahlnn.learnyscape.core.network.datasource.impl.SubmissionDetailsNetworkDataSourceImpl
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

    @Binds
    fun bindsResourceDetailsNetworkDataSource(
        resourceDetailsNetworkDataSource: ResourceDetailsNetworkDataSourceImpl
    ): ResourceDetailsNetworkDataSource

    @Binds
    fun bindsClassMembersNetworkDataSource(
        classMembersNetworkDataSource: ClassMembersNetworkDataSourceImpl
    ): ClassMembersNetworkDataSource

    @Binds
    fun bindsClassFeedNetworkDataSource(
        classFeedNetworkDataSource: ClassFeedNetworkDataSourceImpl
    ): ClassFeedNetworkDataSource

    @Binds
    fun bindsQuizSessionNetworkDataSource(
        quizSessionNetworkDataSource: QuizSessionNetworkDataSourceImpl
    ): QuizSessionNetworkDataSource

    @Binds
    fun bindsSubmissionDetailsNetworkDataSource(
        submissionDetailsNetworkDataSource: SubmissionDetailsNetworkDataSourceImpl
    ): SubmissionDetailsNetworkDataSource

    @Binds
    fun bindsAttachmentNetworkDataSource(
        attachmentNetworkDataSource: AttachmentNetworkDataSourceImpl
    ): AttachmentNetworkDataSource

    @Binds
    fun bindsPendingRequestNetworkDataSource(
        pendingRequestNetworkDataSource: PendingRequestNetworkDataSourceImpl
    ): PendingRequestNetworkDataSource

    @Binds
    fun bindsNotificationsNetworkDataSource(
        notificationsNetworkDataSource: NotificationsNetworkDataSourceImpl
    ): NotificationsNetworkDataSource

    @Binds
    fun bindsChangePasswordNetworkDataSource(
        changePasswordNetworkDataSource: ChangePasswordNetworkDataSourceImpl
    ): ChangePasswordNetworkDataSource
}