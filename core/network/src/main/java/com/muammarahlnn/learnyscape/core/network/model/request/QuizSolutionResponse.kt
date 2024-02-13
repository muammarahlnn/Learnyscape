package com.muammarahlnn.learnyscape.core.network.model.request

import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File QuizSolution, 12/02/2024 21.00
 */
@Serializable
data class QuizSolutionResponse(
    val problems: List<Problem>,
) {

    @Serializable
    data class Problem(
        val solution: String? = null,
    )
}