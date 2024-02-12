package com.muammarahlnn.learnyscape.core.domain.di

import com.muammarahlnn.learnyscape.core.data.repository.QuizSessionRepository
import com.muammarahlnn.learnyscape.core.domain.quizsession.GetQuizMultipleChoiceQuestionsUseCase
import com.muammarahlnn.learnyscape.core.domain.quizsession.SubmitMultipleChoiceAnswersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * @Author Muammar Ahlan Abimanyu
 * @File QuizSessionInteractorModule, 09/02/2024 18.26
 */
@Module
@InstallIn(ViewModelComponent::class)
object QuizSessionInteractorModule {

    @Provides
    @ViewModelScoped
    fun providesGetQuizMultipleChoiceQuestionsUseCase(
        quizSessionRepository: QuizSessionRepository
    ): GetQuizMultipleChoiceQuestionsUseCase = GetQuizMultipleChoiceQuestionsUseCase(
        quizSessionRepository::getQuizMultipleChoiceQuestions
    )

    @Provides
    @ViewModelScoped
    fun providesSubmitMultipleChoiceAnswersUseCase(
        quizSessionRepository: QuizSessionRepository
    ): SubmitMultipleChoiceAnswersUseCase = SubmitMultipleChoiceAnswersUseCase(
        quizSessionRepository::submitMultipleChoiceAnswers
    )
}