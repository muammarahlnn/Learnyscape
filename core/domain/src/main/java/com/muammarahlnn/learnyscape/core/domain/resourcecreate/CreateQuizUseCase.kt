package com.muammarahlnn.learnyscape.core.domain.resourcecreate

import com.muammarahlnn.learnyscape.core.data.repository.ResourceCreateRepository
import com.muammarahlnn.learnyscape.core.model.data.MultipleChoiceQuestionModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File CreateQuizUseCase, 24/01/2024 13.06
 */
class CreateQuizUseCase @Inject constructor(
    private val resourceCreateRepository: ResourceCreateRepository,
) {

    operator fun invoke(
        classId: String,
        title: String,
        description: String,
        quizType: String,
        questions: List<MultipleChoiceQuestionModel>,
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        duration: Int
    ): Flow<String> = resourceCreateRepository.createQuiz(
        classId = classId,
        title = title,
        description = description,
        quizType = quizType,
        startDate = startDate,
        endDate = endDate,
        duration = duration
    ).flatMapLatest { quizId ->
        resourceCreateRepository.addQuizQuestions(
            quizId = quizId,
            questions = questions
        )
    }
}