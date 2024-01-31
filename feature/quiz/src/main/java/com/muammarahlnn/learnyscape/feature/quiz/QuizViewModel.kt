package com.muammarahlnn.learnyscape.feature.quiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.result.asResult
import com.muammarahlnn.learnyscape.core.common.result.onError
import com.muammarahlnn.learnyscape.core.common.result.onException
import com.muammarahlnn.learnyscape.core.common.result.onLoading
import com.muammarahlnn.learnyscape.core.common.result.onNoInternet
import com.muammarahlnn.learnyscape.core.common.result.onSuccess
import com.muammarahlnn.learnyscape.core.domain.resourceoverview.GetQuizzesUseCase
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File QuizViewModel, 14/01/2024 15.01
 */
@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getQuizzesUseCase: GetQuizzesUseCase,
) : ViewModel(), QuizContract {

    private val _state = MutableStateFlow(QuizContract.State())
    override val state: StateFlow<QuizContract.State> = _state

    private val _effect = MutableSharedFlow<QuizContract.Effect>()
    override val effect: SharedFlow<QuizContract.Effect> = _effect

    private val _refreshing = MutableStateFlow(false)
    override val refreshing: StateFlow<Boolean> = _refreshing

    private val moduleOrdinal = ClassResourceType.QUIZ.ordinal

    override fun event(event: QuizContract.Event) {
        when (event) {
            is QuizContract.Event.SetClassId ->
                setClassId(event.classId)

            QuizContract.Event.FetchQuizzes ->
                fetchQuizzes()

            QuizContract.Event.OnNavigateBack ->
                navigateBack()

            is QuizContract.Event.OnNavigateToResourceDetails ->
                navigateToResourceDetails(event.quizId)

            QuizContract.Event.OnNavigateToResourceCreate ->
                navigateToResourceCreate()
        }
    }

    private fun setClassId(classId: String) {
        _state.update {
            it.copy(classId = classId)
        }
    }

    private fun fetchQuizzes() {
        viewModelScope.launch {
            getQuizzesUseCase(state.value.classId)
                .asResult()
                .collect { result ->
                    result.onLoading {
                        _state.update {
                            it.copy(
                                uiState = QuizContract.UiState.Loading
                            )
                        }
                    }.onSuccess { quizzes ->
                        _state.update {
                            it.copy(
                                uiState = if (quizzes.isNotEmpty()) {
                                    QuizContract.UiState.Success(quizzes)
                                } else {
                                    QuizContract.UiState.SuccessEmpty
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
        _state.update {
            it.copy(
                uiState = QuizContract.UiState.Error(message)
            )
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _effect.emit(QuizContract.Effect.NavigateBack)
        }
    }

    private fun navigateToResourceDetails(quizId: String) {
        viewModelScope.launch {
            _effect.emit(
                QuizContract.Effect.NavigateToResourceDetails(
                    resourceId = quizId,
                    resourceTypeOrdinal = moduleOrdinal
                )
            )
        }
    }

    private fun navigateToResourceCreate() {
        viewModelScope.launch {
            _effect.emit(
                QuizContract.Effect.NavigateToResourceCreate(
                    classId = _state.value.classId,
                    resourceTypeOrdinal = moduleOrdinal,
                )
            )
        }
    }
}