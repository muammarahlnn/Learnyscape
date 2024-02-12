package com.muammarahlnn.learnyscape.core.network.model.request

import kotlinx.serialization.Serializable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SubmitMultipleChoiceAnwersRequest, 10/02/2024 21.12
 */
@Serializable
data class SubmitMultipleChoiceAnswersRequest(
    val answers: List<String>
)