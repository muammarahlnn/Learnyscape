package com.muammarahlnn.learnyscape.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File QuizMultipleChoiceProblemResponse, 09/02/2024 18.16
 */
@Serializable
data class QuizMultipleChoiceProblemsResponse(
    val problems: List<QuizMultipleChoiceProblemResponse>
)

@Serializable
data class QuizMultipleChoiceProblemResponse(
    val id: String,
    @SerialName("description") val question: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val optionE: String,
)