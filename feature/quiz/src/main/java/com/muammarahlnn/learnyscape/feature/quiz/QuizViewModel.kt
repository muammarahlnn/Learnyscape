package com.muammarahlnn.learnyscape.feature.quiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.contract.ContractProvider
import com.muammarahlnn.learnyscape.core.common.contract.RefreshProvider
import com.muammarahlnn.learnyscape.core.common.contract.contract
import com.muammarahlnn.learnyscape.core.common.contract.refresh
import com.muammarahlnn.learnyscape.core.common.result.asResult
import com.muammarahlnn.learnyscape.core.common.result.onError
import com.muammarahlnn.learnyscape.core.common.result.onException
import com.muammarahlnn.learnyscape.core.common.result.onLoading
import com.muammarahlnn.learnyscape.core.common.result.onNoInternet
import com.muammarahlnn.learnyscape.core.common.result.onSuccess
import com.muammarahlnn.learnyscape.core.domain.resourceoverview.GetQuizzesUseCase
import com.muammarahlnn.learnyscape.feature.quiz.QuizContract.Effect
import com.muammarahlnn.learnyscape.feature.quiz.QuizContract.Event
import com.muammarahlnn.learnyscape.feature.quiz.QuizContract.State
import com.muammarahlnn.learnyscape.feature.quiz.QuizContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File QuizViewModel, 14/01/2024 15.01
 */
@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getQuizzesUseCase: GetQuizzesUseCase,
) : ViewModel(),
    ContractProvider<State, Event, Effect> by contract(State()),
    RefreshProvider by refresh()
{

    override fun event(event: Event) {
        when (event) {
            is Event.SetClassId -> setClassId(event.classId)
            Event.FetchQuizzes -> fetchQuizzes()
        }
    }

    private fun setClassId(classId: String) {
        updateState {
            it.copy(classId = classId)
        }
    }

    private fun fetchQuizzes() {
        viewModelScope.launch {
            getQuizzesUseCase(state.value.classId)
                .asResult()
                .collect { result ->
                    result.onLoading {
                        updateState {
                            it.copy(
                                uiState = UiState.Loading
                            )
                        }
                    }.onSuccess { quizzes ->
                        updateState {
                            it.copy(
                                uiState = if (quizzes.isNotEmpty()) {
                                    UiState.Success(quizzes)
                                } else {
                                    UiState.SuccessEmpty
                                }
                            )
                        }
                    }.onNoInternet { message ->
                        onErrorGetQuizzes(message)
                    }.onError { _, message ->
                        onErrorGetQuizzes(message)
                    }.onException { exception, message ->
                        Log.e("QuizViewModel", exception?.message.toString())
                        onErrorGetQuizzes(message)
                    }
                }
        }
    }

    private fun onErrorGetQuizzes(message: String) {
        updateState {
            it.copy(
                uiState = UiState.Error(message)
            )
        }
    }
}