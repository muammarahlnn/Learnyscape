package com.muammarahlnn.learnyscape.feature.quizsession

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.result.asResult
import com.muammarahlnn.learnyscape.core.common.result.onError
import com.muammarahlnn.learnyscape.core.common.result.onException
import com.muammarahlnn.learnyscape.core.common.result.onLoading
import com.muammarahlnn.learnyscape.core.common.result.onNoInternet
import com.muammarahlnn.learnyscape.core.common.result.onSuccess
import com.muammarahlnn.learnyscape.core.domain.quizsession.GetQuizMultipleChoiceQuestionsUseCase
import com.muammarahlnn.learnyscape.core.domain.quizsession.SubmitMultipleChoiceAnswersUseCase
import com.muammarahlnn.learnyscape.core.model.data.MultipleChoiceQuestionModel
import com.muammarahlnn.learnyscape.core.model.data.QuizType
import com.muammarahlnn.learnyscape.feature.quizsession.navigation.QuizSessionArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file QuizSessionViewModel, 28/08/2023 21.45 by Muammar Ahlan Abimanyu
 */
@HiltViewModel
class QuizSessionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getQuizMultipleChoiceQuestionsUseCase: GetQuizMultipleChoiceQuestionsUseCase,
    private val submitMultipleChoiceAnswersUseCase: SubmitMultipleChoiceAnswersUseCase,
) : ViewModel(), QuizSessionContract {

    private val quizSessionArgs = QuizSessionArgs(savedStateHandle)

    private val _state = MutableStateFlow(
        QuizSessionContract.State(
            quizId = quizSessionArgs.quizId,
            quizType = QuizType.getQuizType(quizSessionArgs.quizTypeOrdinal),
            quizName = quizSessionArgs.quizName,
            quizDuration = quizSessionArgs.quizDuration,
        )
    )
    override val state: StateFlow<QuizSessionContract.State> = _state

    private val _effect = MutableSharedFlow<QuizSessionContract.Effect>()
    override val effect: SharedFlow<QuizSessionContract.Effect> = _effect

    override fun event(event: QuizSessionContract.Event) {
        when (event) {
            QuizSessionContract.Event.FetchQuizQuestions ->
                fetchQuizQuestions()

            QuizSessionContract.Event.OnQuizIsOver ->
                navigateBack()

            is QuizSessionContract.Event.ShowYouCanNotLeaveDialog ->
                showYouCanNotLeaveDialog(event.show)

            is QuizSessionContract.Event.ShowSubmitAnswerDialog ->
                showSubmitAnswerDialog(event.show)

            is QuizSessionContract.Event.ShowTimeoutDialog ->
                showTimeoutDialog(event.show)

            is QuizSessionContract.Event.ShowUnansweredQuestionsDialog ->
                showUnansweredQuestionsDialog(event.show)

            is QuizSessionContract.Event.ShowSubmittingAnswersDialog ->
                showSubmittingAnswersDialog(event.show)

            is QuizSessionContract.Event.OnOptionSelected ->
                onOptionSelected(event.index, event.option)

            QuizSessionContract.Event.OnSubmitAnswers ->
                submitAnswers()
        }
    }

    private fun fetchQuizQuestions() {

        fun onErrorFetchQuizQuestions(message: String) {
            _state.update {
                it.copy(
                    uiState = QuizSessionContract.UiState.Error(message),
                )
            }
        }

        viewModelScope.launch {
            getQuizMultipleChoiceQuestionsUseCase(state.value.quizId)
                .asResult()
                .collect { result ->
                    result.onLoading {
                        _state.update {
                            it.copy(
                                uiState = QuizSessionContract.UiState.Loading,
                            )
                        }
                    }.onSuccess { multipleChoiceQuestions ->
                        _state.update {
                            it.copy(
                                uiState = QuizSessionContract.UiState.Success,
                                multipleChoiceQuestions = multipleChoiceQuestions.mapIndexed { index, model ->
                                    model.toMultipleChoiceQuestion(index)
                                },
                                multipleChoiceAnswers = List(multipleChoiceQuestions.size) {
                                    OptionLetter.UNSELECTED
                                }
                            )
                        }
                    }.onNoInternet { message ->
                        onErrorFetchQuizQuestions(message)
                    }.onError { _, message ->
                        onErrorFetchQuizQuestions(message)
                    }.onException { exception, message ->
                        Log.e(TAG, exception?.message.toString())
                        onErrorFetchQuizQuestions(message)
                    }
                }
        }
    }

    private fun MultipleChoiceQuestionModel.toMultipleChoiceQuestion(index: Int) =
        MultipleChoiceQuestion(
            id = index,
            question = question,
            options = listOf(
                Option(
                    letter = OptionLetter.A,
                    text = options.optionA.orEmpty()
                ),
                Option(
                    letter = OptionLetter.B,
                    text = options.optionB.orEmpty()
                ),
                Option(
                    letter = OptionLetter.C,
                    text = options.optionC.orEmpty()
                ),
                Option(
                    letter = OptionLetter.D,
                    text = options.optionD.orEmpty()
                ),
                Option(
                    letter = OptionLetter.E,
                    text = options.optionE.orEmpty()
                ),
            )
        )

    private fun showYouCanNotLeaveDialog(show: Boolean) {
        _state.update {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    showYouCanNotLeaveDialog = show
                )
            )
        }
    }

    private fun showSubmitAnswerDialog(show: Boolean) {
        _state.update {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    showSubmitAnswerDialog = show
                )
            )
        }
    }

    private fun showTimeoutDialog(show: Boolean) {
        _state.update {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    showTimeoutDialog = show
                )
            )
        }
    }

    private fun showUnansweredQuestionsDialog(show: Boolean) {
        _state.update {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    showUnansweredQuestionsDialog = show
                )
            )
        }
    }

    private fun showSubmittingAnswersDialog(show: Boolean) {
        _state.update {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    showSubmittingAnswersDialog = show
                )
            )
        }
    }

    private fun onOptionSelected(index: Int, option: OptionLetter) {
        _state.update {
            it.copy(
                multipleChoiceAnswers = it.multipleChoiceAnswers.toMutableList().apply {
                    this[index] = option
                }.toList()
            )
        }
    }

    private fun submitAnswers() {
        showSubmitAnswerDialog(false)

        val unansweredQuestions = mutableListOf<Int>()
        state.value.multipleChoiceAnswers.forEachIndexed { index, option ->
            if (option == OptionLetter.UNSELECTED) {
                unansweredQuestions.add(index + 1)
            }
        }
        if (unansweredQuestions.isNotEmpty()) {
            var unansweredQuestionsString = ""
            unansweredQuestions.forEachIndexed { index, number ->
                if (unansweredQuestions.size > 1 && index == unansweredQuestions.size - 1)
                    unansweredQuestionsString += " and "
                unansweredQuestionsString += number
                if (index < unansweredQuestions.size - 2)
                    unansweredQuestionsString += ", "
            }

            _state.update {
                it.copy(
                    unansweredQuestions = unansweredQuestionsString
                )
            }
            showUnansweredQuestionsDialog(true)

            return
        }

        fun onErrorSubmitAnswers(message: String) {
            _state.update {
                it.copy(
                    submittingAnswersDialogUiState = QuizSessionContract.UiState.Error(message)
                )
            }
        }

        showSubmittingAnswersDialog(true)
        viewModelScope.launch {
            submitMultipleChoiceAnswersUseCase(
                quizId = state.value.quizId,
                answers = state.value.multipleChoiceAnswers.map {
                    it.name
                }
            ).asResult().collect { result ->
                result.onLoading {
                    _state.update {
                        it.copy(
                            submittingAnswersDialogUiState = QuizSessionContract.UiState.Loading
                        )
                    }
                }.onSuccess {
                    _state.update {
                        it.copy(
                            submittingAnswersDialogUiState = QuizSessionContract.UiState.Success
                        )
                    }
                }.onNoInternet { message ->
                    onErrorSubmitAnswers(message)
                }.onError { _, message ->
                    onErrorSubmitAnswers(message)
                }.onException { exception, message ->
                    Log.e(TAG, exception?.message.toString())
                    onErrorSubmitAnswers(message)
                }
            }
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _effect.emit(QuizSessionContract.Effect.NavigateBack)
        }
    }

    companion object {

        private const val TAG = "QuizSessionViewModel"
    }
}