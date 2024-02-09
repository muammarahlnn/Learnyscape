package com.muammarahlnn.learnyscape.core.data.mapper

import com.muammarahlnn.learnyscape.core.model.data.MultipleChoiceQuestionModel
import com.muammarahlnn.learnyscape.core.network.model.response.QuizMultipleChoiceProblemResponse

/**
 * @Author Muammar Ahlan Abimanyu
 * @File QuizSessionMapper, 09/02/2024 18.23
 */

fun List<QuizMultipleChoiceProblemResponse>.toMultipleChoiceQuestionModels() = map {
    it.toMultipleChoiceQuestionModel()
}

fun QuizMultipleChoiceProblemResponse.toMultipleChoiceQuestionModel() = MultipleChoiceQuestionModel(
    question = question,
    options = MultipleChoiceQuestionModel.Options(
        optionA = optionA,
        optionB = optionB,
        optionC = optionC,
        optionD = optionD,
        optionE = optionE,
    )
)