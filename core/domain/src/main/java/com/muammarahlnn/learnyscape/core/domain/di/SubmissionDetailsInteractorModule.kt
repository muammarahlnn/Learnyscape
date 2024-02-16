package com.muammarahlnn.learnyscape.core.domain.di

import com.muammarahlnn.learnyscape.core.data.repository.SubmissionDetailsRepository
import com.muammarahlnn.learnyscape.core.domain.submissiondetails.GetAssignmentSubmissionDetailsUseCase
import com.muammarahlnn.learnyscape.core.domain.submissiondetails.GetStudentQuizAnswersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SubmissionDetailsInteractorModule, 13/02/2024 23.32
 */
@Module
@InstallIn(ViewModelComponent::class)
object SubmissionDetailsInteractorModule {

    @Provides
    @ViewModelScoped
    fun providesGetAssignmentSubmissionDetailsUseCase(
        submissionDetailsRepository: SubmissionDetailsRepository
    ): GetAssignmentSubmissionDetailsUseCase = GetAssignmentSubmissionDetailsUseCase(
        submissionDetailsRepository::getAssignmentSubmissionDetails
    )

    @Provides
    @ViewModelScoped
    fun providesGetStudentQuizAnswersUseCase(
        submissionDetailsRepository: SubmissionDetailsRepository
    ): GetStudentQuizAnswersUseCase = GetStudentQuizAnswersUseCase(
        submissionDetailsRepository::getStudentQuizAnswers
    )
}