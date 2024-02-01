package com.muammarahlnn.learnyscape.core.domain.di

import com.muammarahlnn.learnyscape.core.data.repository.ResourceDetailsRepository
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.DeleteAnnouncementUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.DeleteAssignmentUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.DeleteModuleUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.GetAnnouncementDetailsUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.GetAssignmentDetailsUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.GetAssignmentSubmissionsUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.GetModuleDetailsUseCase
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.GetQuizDetailsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceDetailsInteractorModule, 18/01/2024 17.50
 */
@Module
@InstallIn(ViewModelComponent::class)
object ResourceDetailsInteractorModule {

    @Provides
    @ViewModelScoped
    fun providesGetAnnouncementDetailsUseCase(
        resourceDetailsRepository: ResourceDetailsRepository
    ): GetAnnouncementDetailsUseCase = GetAnnouncementDetailsUseCase(
        resourceDetailsRepository::getAnnouncementDetails
    )

    @Provides
    @ViewModelScoped
    fun providesDeleteAnnouncementUseCase(
        resourceDetailsRepository: ResourceDetailsRepository
    ): DeleteAnnouncementUseCase = DeleteAnnouncementUseCase(
        resourceDetailsRepository::deleteAnnouncement
    )

    @ViewModelScoped
    @Provides
    fun providesGetModuleDetailsUseCase(
        resourceDetailsRepository: ResourceDetailsRepository
    ): GetModuleDetailsUseCase = GetModuleDetailsUseCase(
        resourceDetailsRepository::getModuleDetails
    )

    @ViewModelScoped
    @Provides
    fun providesDeleteModuleUseCase(
        resourceDetailsRepository: ResourceDetailsRepository
    ): DeleteModuleUseCase = DeleteModuleUseCase(
        resourceDetailsRepository::deleteModule
    )

    @Provides
    @ViewModelScoped
    fun providesGetAssignmentDetailsUseCase(
        resourceDetailsRepository: ResourceDetailsRepository
    ): GetAssignmentDetailsUseCase = GetAssignmentDetailsUseCase(
        resourceDetailsRepository::getAssignmentDetails
    )

    @Provides
    @ViewModelScoped
    fun providesDeleteAssignmentUseCase(
        resourceDetailsRepository: ResourceDetailsRepository
    ): DeleteAssignmentUseCase = DeleteAssignmentUseCase(
        resourceDetailsRepository::deleteAssignment
    )

    @Provides
    @ViewModelScoped
    fun providesGetQuizDetailsUseCase(
        resourceDetailsRepository: ResourceDetailsRepository
    ): GetQuizDetailsUseCase = GetQuizDetailsUseCase(
        resourceDetailsRepository::getQuizDetails
    )

    @Provides
    @ViewModelScoped
    fun providesGetAssignmentSubmissionsUseCase(
        resourceDetailsRepository: ResourceDetailsRepository
    ): GetAssignmentSubmissionsUseCase = GetAssignmentSubmissionsUseCase(
        resourceDetailsRepository::getAssignmentSubmissions
    )
}