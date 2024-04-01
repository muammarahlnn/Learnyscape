package com.muammarahlnn.learnyscape.core.model.data

/**
 * @Author Muammar Ahlan Abimanyu
 * @File StudentQuizAnswerModel, 16/02/2024 12.00
 */
data class StudentQuizAnswerModel(
    val id: String,
    val question: String,
    val optionA: QuestionOption,
    val optionB: QuestionOption,
    val optionC: QuestionOption,
    val optionD: QuestionOption,
    val optionE: QuestionOption,
    val solution: Option,
) {

    class QuestionOption(
        val option: Option,
        val description: String,
    )

    enum class Option {
        A, B, C, D, E,
    }
}