package com.muammarahlnn.learnyscape.core.data.mapper

import com.muammarahlnn.learnyscape.core.model.data.StudentQuizAnswerModel
import com.muammarahlnn.learnyscape.core.model.data.StudentQuizAnswerModel.Option.A
import com.muammarahlnn.learnyscape.core.model.data.StudentQuizAnswerModel.Option.B
import com.muammarahlnn.learnyscape.core.model.data.StudentQuizAnswerModel.Option.C
import com.muammarahlnn.learnyscape.core.model.data.StudentQuizAnswerModel.Option.D
import com.muammarahlnn.learnyscape.core.model.data.StudentQuizAnswerModel.Option.E
import com.muammarahlnn.learnyscape.core.network.model.response.StudentQuizAnswerResponse

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SubmissionDetailsMapper, 16/02/2024 12.06
 */
fun List<StudentQuizAnswerResponse>.toStudentQuizAnswerModels() = map {
    it.toStudentQuizAnswerModel()
}

fun StudentQuizAnswerResponse.toStudentQuizAnswerModel() = StudentQuizAnswerModel(
    id = id,
    question = description,
    optionA = StudentQuizAnswerModel.QuestionOption(
        option = A,
        description = optionA,
    ),
    optionB = StudentQuizAnswerModel.QuestionOption(
        option = B,
        description = optionB,
    ),
    optionC = StudentQuizAnswerModel.QuestionOption(
        option = C,
        description = optionC,
    ),
    optionD = StudentQuizAnswerModel.QuestionOption(
        option = D,
        description = optionD,
    ),
    optionE = StudentQuizAnswerModel.QuestionOption(
        option = E,
        description = optionE,
    ),
    solution = when (solution) {
        StudentQuizAnswerResponse.Option.A -> A
        StudentQuizAnswerResponse.Option.B -> B
        StudentQuizAnswerResponse.Option.C -> C
        StudentQuizAnswerResponse.Option.D -> D
        StudentQuizAnswerResponse.Option.E -> E
    },
)