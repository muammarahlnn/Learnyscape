package com.muammarahlnn.learnyscape.core.network.model.response

import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File StudentQuizAnswersResponse, 16/02/2024 11.54
 */
@Serializable
data class StudentQuizAnswerResponse(
    val id: String,
    val description: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val optionE: String,
    val solution: Option,
) {

    enum class Option {
        A, B, C, D, E
    }
}