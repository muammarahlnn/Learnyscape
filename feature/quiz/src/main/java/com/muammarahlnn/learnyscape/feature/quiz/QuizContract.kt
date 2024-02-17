package com.muammarahlnn.learnyscape.feature.quiz

import com.muammarahlnn.learnyscape.core.model.data.QuizOverviewModel
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType

/**
 * @Author Muammar Ahlan Abimanyu
 * @File QuizContract, 14/01/2024 15.00
 */
interface QuizContract {

    data class State(
        val classId: String = "",
        val uiState: UiState = UiState.Loading,
        val moduleOrdinal: Int = ClassResourceType.QUIZ.ordinal
    )

    sealed interface UiState {

        data object Loading : UiState

        data class Success(val quizzes: List<QuizOverviewModel>) : UiState

        data object SuccessEmpty : UiState

        data class Error(val message: String) : UiState
    }

    sealed interface Event {

        data class SetClassId(val classId: String) : Event

        data object FetchQuizzes : Event
    }

    sealed interface Effect
}