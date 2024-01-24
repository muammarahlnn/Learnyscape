package com.muammarahlnn.learnyscape.core.network.model.request

import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AddQuizQuestionsRequest, 24/01/2024 13.15
 */
@Serializable
data class AddQuizQuestionsRequest(
    val problems: List<Problem>,
) {

    @Serializable
    data class Problem(
        val description: String,
        val optionA: String? = null,
        val optionB: String? = null,
        val optionC: String? = null,
        val optionD: String? = null,
        val optionE: String? = null,
    )
}
