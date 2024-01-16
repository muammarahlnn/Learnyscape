package com.muammarahlnn.learnyscape.feature.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @Author Muammar Ahlan Abimanyu
 * @File QuizViewModel, 14/01/2024 15.01
 */
class QuizViewModel : ViewModel(), QuizContract {

    private val _state = MutableStateFlow(QuizContract.State())
    override val state: StateFlow<QuizContract.State> = _state

    private val _effect = MutableSharedFlow<QuizContract.Effect>()
    override val effect: SharedFlow<QuizContract.Effect> = _effect

    private val moduleOrdinal = ClassResourceType.QUIZ.ordinal

    override fun event(event: QuizContract.Event) {
        when (event) {
            is QuizContract.Event.SetClassId ->
                setClassId(event.classId)

            QuizContract.Event.OnNavigateBack ->
                navigateBack()

            QuizContract.Event.OnNavigateToResourceDetails ->
                navigateToResourceDetails()

            QuizContract.Event.OnNavigateToResourceCreate ->
                navigateToResourceCreate()
        }
    }

    private fun setClassId(classId: String) {
        _state.update {
            it.copy(classId = classId)
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _effect.emit(QuizContract.Effect.NavigateBack)
        }
    }

    private fun navigateToResourceDetails() {
        viewModelScope.launch {
            _effect.emit(
                QuizContract.Effect.NavigateToResourceDetails(
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