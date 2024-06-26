package com.muammarahlnn.learnyscape.core.data.di

import com.muammarahlnn.learnyscape.core.data.repository.AvailableClassRepository
import com.muammarahlnn.learnyscape.core.data.repository.CapturedPhotoRepository
import com.muammarahlnn.learnyscape.core.data.repository.ChangePasswordRepository
import com.muammarahlnn.learnyscape.core.data.repository.ClassFeedRepository
import com.muammarahlnn.learnyscape.core.data.repository.ClassMembersRepository
import com.muammarahlnn.learnyscape.core.data.repository.FileRepository
import com.muammarahlnn.learnyscape.core.data.repository.HomeRepository
import com.muammarahlnn.learnyscape.core.data.repository.JoinRequestRepository
import com.muammarahlnn.learnyscape.core.data.repository.LoginRepository
import com.muammarahlnn.learnyscape.core.data.repository.NotificationsRepository
import com.muammarahlnn.learnyscape.core.data.repository.PendingRequestRepository
import com.muammarahlnn.learnyscape.core.data.repository.ProfileRepository
import com.muammarahlnn.learnyscape.core.data.repository.QuizSessionRepository
import com.muammarahlnn.learnyscape.core.data.repository.ResourceCreateRepository
import com.muammarahlnn.learnyscape.core.data.repository.ResourceDetailsRepository
import com.muammarahlnn.learnyscape.core.data.repository.ResourceOverviewRepository
import com.muammarahlnn.learnyscape.core.data.repository.SchedulesRepository
import com.muammarahlnn.learnyscape.core.data.repository.SubmissionDetailsRepository
import com.muammarahlnn.learnyscape.core.data.repository.impl.AvailableClassRepositoryImpl
import com.muammarahlnn.learnyscape.core.data.repository.impl.CapturedPhotoRepositoryImpl
import com.muammarahlnn.learnyscape.core.data.repository.impl.ChangePasswordRepositoryImpl
import com.muammarahlnn.learnyscape.core.data.repository.impl.ClassFeedRepositoryImpl
import com.muammarahlnn.learnyscape.core.data.repository.impl.ClassMembersRepositoryImpl
import com.muammarahlnn.learnyscape.core.data.repository.impl.FileRepositoryImpl
import com.muammarahlnn.learnyscape.core.data.repository.impl.HomeRepositoryImpl
import com.muammarahlnn.learnyscape.core.data.repository.impl.JoinRequestRepositoryImpl
import com.muammarahlnn.learnyscape.core.data.repository.impl.LoginRepositoryImpl
import com.muammarahlnn.learnyscape.core.data.repository.impl.NotificationsRepositoryImpl
import com.muammarahlnn.learnyscape.core.data.repository.impl.PendingRequestRepositoryImpl
import com.muammarahlnn.learnyscape.core.data.repository.impl.ProfileRepositoryImpl
import com.muammarahlnn.learnyscape.core.data.repository.impl.QuizSessionRepositoryImpl
import com.muammarahlnn.learnyscape.core.data.repository.impl.ResourceCreateRepositoryImpl
import com.muammarahlnn.learnyscape.core.data.repository.impl.ResourceDetailsRepositoryImpl
import com.muammarahlnn.learnyscape.core.data.repository.impl.ResourceOverviewRepositoryImpl
import com.muammarahlnn.learnyscape.core.data.repository.impl.SchedulesRepositoryImpl
import com.muammarahlnn.learnyscape.core.data.repository.impl.SubmissionDetailsRepositoryImpl
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

    @Binds
    fun bindsSchedulesRepository(
        schedulesRepository: SchedulesRepositoryImpl
    ): SchedulesRepository

    @Binds
    fun bindsResourceCreateRepository(
        resourceCreateRepository: ResourceCreateRepositoryImpl
    ): ResourceCreateRepository

    @Binds
    fun bindsResourceOverviewRepository(
        resourceOverviewRepository: ResourceOverviewRepositoryImpl
    ): ResourceOverviewRepository

    @Binds
    fun bindsJoinRequestRepository(
        joinRequestRepository: JoinRequestRepositoryImpl
    ): JoinRequestRepository

    @Binds
    fun bindsResourceDetailsRepository(
        resourceDetailsRepository: ResourceDetailsRepositoryImpl
    ): ResourceDetailsRepository

    @Binds
    fun bindsClassMembersRepository(
        classMembersRepository: ClassMembersRepositoryImpl
    ): ClassMembersRepository

    @Binds
    fun bindsClassFeedRepository(
        classFeedRepository: ClassFeedRepositoryImpl
    ): ClassFeedRepository

    @Binds
    fun bindsQuizSessionRepository(
        quizSessionRepository: QuizSessionRepositoryImpl
    ): QuizSessionRepository

    @Binds
    fun bindsSubmissionDetailsRepository(
        submissionDetailsRepository: SubmissionDetailsRepositoryImpl
    ): SubmissionDetailsRepository

    @Binds
    fun bindsPendingRequestRepository(
        pendingRequestRepository: PendingRequestRepositoryImpl
    ): PendingRequestRepository

    @Binds
    fun bindsNotificationsRepository(
        notificationsRepository: NotificationsRepositoryImpl
    ): NotificationsRepository

    @Binds
    fun bindsChangePasswordRepository(
        changePasswordRepository: ChangePasswordRepositoryImpl
    ): ChangePasswordRepository
}