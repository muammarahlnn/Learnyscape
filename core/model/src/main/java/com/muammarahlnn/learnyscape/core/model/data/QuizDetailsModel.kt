package com.muammarahlnn.learnyscape.core.model.data

/**
 * @Author Muammar Ahlan Abimanyu
 * @File QuizDetailsModel, 31/01/2024 01.51
 */
data class QuizDetailsModel(
    val id: String,
    val name: String,
    val updatedAt: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val duration: Int,
    val quizType: QuizType,
)