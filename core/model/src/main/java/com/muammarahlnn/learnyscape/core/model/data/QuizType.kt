package com.muammarahlnn.learnyscape.core.model.data

/**
 * @Author Muammar Ahlan Abimanyu
 * @File QuizType, 17/01/2024 14.08
 */
enum class QuizType {
    NONE,
    MULTIPLE_CHOICE,
    PHOTO_ANSWER;

    companion object {

        fun getQuizType(quizTypeOrdinal: Int): QuizType = when (quizTypeOrdinal) {
            NONE.ordinal -> NONE
            MULTIPLE_CHOICE.ordinal -> MULTIPLE_CHOICE
            PHOTO_ANSWER.ordinal -> PHOTO_ANSWER
            else -> throw IllegalArgumentException("The given ordinal not matched any QuizType")
        }
    }
}