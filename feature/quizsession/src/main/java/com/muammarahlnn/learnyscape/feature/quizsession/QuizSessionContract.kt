package com.muammarahlnn.learnyscape.feature.quizsession

import com.muammarahlnn.learnyscape.core.model.data.QuizType

/**
 * @Author Muammar Ahlan Abimanyu
 * @File QuizSessionContract, 09/02/2024 18.31
 */
interface QuizSessionContract {

    data class State(
        val uiState: UiState = UiState.Loading,
        val submittingAnswersDialogUiState: SubmittingAnswersDialogUiState =
            SubmittingAnswersDialogUiState.None,
        val quizId: String = "",
        val quizType: QuizType = QuizType.NONE,
        val quizName: String = "",
        val quizDuration: Int = 0,
        val overlayComposableVisibility: OverlayComposableVisibility = OverlayComposableVisibility(),
        val multipleChoiceQuestions: List<MultipleChoiceQuestion> = listOf(),
        val multipleChoiceAnswers: List<OptionLetter> = listOf(),
        val unansweredQuestions: String = "",
    )

    sealed interface UiState {

        data object Loading : UiState

        data object Success : UiState

        data class Error(val message: String) : UiState
    }

    sealed interface SubmittingAnswersDialogUiState {

        data object None : SubmittingAnswersDialogUiState

        data object Loading : SubmittingAnswersDialogUiState

        data object Success : SubmittingAnswersDialogUiState

        data class Error(val message: String) : SubmittingAnswersDialogUiState
    }

    data class OverlayComposableVisibility(
        val showSubmitAnswerDialog: Boolean = false,
        val showTimeoutDialog: Boolean = false,
        val showYouCanNotLeaveDialog: Boolean = false,
        val showUnansweredQuestionsDialog: Boolean = false,
        val showSubmittingAnswersDialog: Boolean = false,
    )

    sealed interface Event {

        data object FetchQuizQuestions : Event

        data class ShowYouCanNotLeaveDialog(val show: Boolean) : Event

        data class ShowSubmitAnswerDialog(val show: Boolean) : Event

        data class ShowTimeoutDialog(val show: Boolean) : Event

        data class ShowUnansweredQuestionsDialog(val show: Boolean) : Event

        data class ShowSubmittingAnswersDialog(val show: Boolean) : Event

        data class OnOptionSelected(
            val index: Int,
            val option: OptionLetter,
        ) : Event

        data object OnSubmitAnswers : Event
    }

    sealed interface Effect
}