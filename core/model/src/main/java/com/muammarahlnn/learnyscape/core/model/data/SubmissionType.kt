package com.muammarahlnn.learnyscape.core.model.data

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SubmissionType, 03/02/2024 15.24
 */
enum class SubmissionType {
    ASSIGNMENT, QUIZ;

    companion object {

        fun getSubmissionType(submissionTypeOrdinal: Int): SubmissionType =
            when (submissionTypeOrdinal) {
                ASSIGNMENT.ordinal -> ASSIGNMENT
                QUIZ.ordinal -> QUIZ
                else -> throw IllegalArgumentException("The given ordinal not matched any SubmissionType")
            }
    }
}