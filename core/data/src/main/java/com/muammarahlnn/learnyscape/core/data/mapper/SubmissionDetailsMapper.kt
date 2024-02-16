package com.muammarahlnn.learnyscape.core.data.mapper

import com.muammarahlnn.learnyscape.core.model.data.StudentQuizAnswerModel
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
    option = solution.name,
    description = when (solution) {
        StudentQuizAnswerResponse.Option.A -> optionA
        StudentQuizAnswerResponse.Option.B -> optionB
        StudentQuizAnswerResponse.Option.C -> optionC
        StudentQuizAnswerResponse.Option.D -> optionD
        StudentQuizAnswerResponse.Option.E -> optionE
    }
)