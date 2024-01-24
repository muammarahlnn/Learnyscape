package com.muammarahlnn.learnyscape.core.model.data

/**
 * @Author Muammar Ahlan Abimanyu
 * @File MultipleChoiceQuestionModel, 24/01/2024 14.32
 */
data class MultipleChoiceQuestionModel(
    val question: String,
    val options: Options = Options(),
) {
    data class Options(
        val optionA: String? = null,
        val optionB: String? = null,
        val optionC: String? = null,
        val optionD: String? = null,
        val optionE: String? = null,
    )
}