package com.muammarahlnn.learnyscape.feature.quizsession

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.contract.ContractProvider
import com.muammarahlnn.learnyscape.core.common.contract.contract
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
import com.muammarahlnn.learnyscape.feature.quizsession.QuizSessionContract.Effect
import com.muammarahlnn.learnyscape.feature.quizsession.QuizSessionContract.Event
import com.muammarahlnn.learnyscape.feature.quizsession.QuizSessionContract.State
import com.muammarahlnn.learnyscape.feature.quizsession.QuizSessionContract.SubmittingAnswersDialogUiState
import com.muammarahlnn.learnyscape.feature.quizsession.QuizSessionContract.UiState
import com.muammarahlnn.learnyscape.feature.quizsession.navigation.QuizSessionArgs
import dagger.hilt.android.lifecycle.HiltViewModel
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
) : ViewModel(), ContractProvider<State, Event, Effect> by contract(State()) {

    private val quizSessionArgs = QuizSessionArgs(savedStateHandle)

    init {
        updateState {
            it.copy(
                quizId = quizSessionArgs.quizId,
                quizType = QuizType.getQuizType(quizSessionArgs.quizTypeOrdinal),
                quizName = quizSessionArgs.quizName,
                quizDuration = quizSessionArgs.quizDuration,
            )
        }
    }

    override fun event(event: Event) {
        when (event) {
            Event.FetchQuizQuestions ->
                fetchQuizQuestions()

            is Event.ShowYouCanNotLeaveDialog ->
                showYouCanNotLeaveDialog(event.show)

            is Event.ShowSubmitAnswerDialog ->
                showSubmitAnswerDialog(event.show)

            is Event.ShowTimeoutDialog ->
                showTimeoutDialog(event.show)

            is Event.ShowUnansweredQuestionsDialog ->
                showUnansweredQuestionsDialog(event.show)

            is Event.ShowSubmittingAnswersDialog ->
                showSubmittingAnswersDialog(event.show)

            is Event.OnOptionSelected ->
                onOptionSelected(event.index, event.option)

            Event.OnSubmitAnswers ->
                submitAnswers()
        }
    }

    private fun fetchQuizQuestions() {

        fun onErrorFetchQuizQuestions(message: String) {
            updateState {
                it.copy(
                    uiState = UiState.Error(message),
                )
            }
        }

        viewModelScope.launch {
            getQuizMultipleChoiceQuestionsUseCase(state.value.quizId)
                .asResult()
                .collect { result ->
                    result.onLoading {
                        updateState {
                            it.copy(
                                uiState = UiState.Loading,
                            )
                        }
                    }.onSuccess { multipleChoiceQuestions ->
                        updateState {
                            it.copy(
                                uiState = UiState.Success,
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
        updateState {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    showYouCanNotLeaveDialog = show
                )
            )
        }
    }

    private fun showSubmitAnswerDialog(show: Boolean) {
        updateState {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    showSubmitAnswerDialog = show
                )
            )
        }
    }

    private fun showTimeoutDialog(show: Boolean) {
        updateState {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    showTimeoutDialog = show
                )
            )
        }
    }

    private fun showUnansweredQuestionsDialog(show: Boolean) {
        updateState {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    showUnansweredQuestionsDialog = show
                )
            )
        }
    }

    private fun showSubmittingAnswersDialog(show: Boolean) {
        updateState {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    showSubmittingAnswersDialog = show
                )
            )
        }
    }

    private fun onOptionSelected(index: Int, option: OptionLetter) {
        updateState {
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

            updateState {
                it.copy(
                    unansweredQuestions = unansweredQuestionsString
                )
            }
            showUnansweredQuestionsDialog(true)

            return
        }

        fun onErrorSubmitAnswers(message: String) {
            updateState {
                it.copy(
                    submittingAnswersDialogUiState = SubmittingAnswersDialogUiState.Error(message)
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
                    updateState {
                        it.copy(
                            submittingAnswersDialogUiState = SubmittingAnswersDialogUiState.Loading
                        )
                    }
                }.onSuccess {
                    updateState {
                        it.copy(
                            submittingAnswersDialogUiState = SubmittingAnswersDialogUiState.Success
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

    companion object {

        private const val TAG = "QuizSessionViewModel"
    }
}