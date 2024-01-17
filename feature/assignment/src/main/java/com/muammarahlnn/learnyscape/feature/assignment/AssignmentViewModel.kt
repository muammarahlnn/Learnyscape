package com.muammarahlnn.learnyscape.feature.assignment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.result.asResult
import com.muammarahlnn.learnyscape.core.common.result.onError
import com.muammarahlnn.learnyscape.core.common.result.onException
import com.muammarahlnn.learnyscape.core.common.result.onLoading
import com.muammarahlnn.learnyscape.core.common.result.onNoInternet
import com.muammarahlnn.learnyscape.core.common.result.onSuccess
import com.muammarahlnn.learnyscape.core.domain.resourceoverview.GetAssignmentsUseCase
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
 * @File AssignmentViewModel, 14/01/2024 14.55
 */
@HiltViewModel
class AssignmentViewModel @Inject constructor(
    private val getAssignmentsUseCase: GetAssignmentsUseCase,
) : ViewModel(), AssignmentContract {

    private val _state = MutableStateFlow(AssignmentContract.State())
    override val state: StateFlow<AssignmentContract.State> = _state

    private val _effect = MutableSharedFlow<AssignmentContract.Effect>()
    override val effect: SharedFlow<AssignmentContract.Effect> = _effect

    private val _refreshing = MutableStateFlow(false)
    override val refreshing: StateFlow<Boolean> = _refreshing

    private val assignmentOrdinal = ClassResourceType.ASSIGNMENT.ordinal

    override fun event(event: AssignmentContract.Event) {
        when (event) {
            is AssignmentContract.Event.SetClassId ->
                setClassId(event.classId)

            is AssignmentContract.Event.FetchAssignments ->
                fetchAssignments()

            AssignmentContract.Event.OnNavigateBack ->
                navigateBack()

            AssignmentContract.Event.OnNavigateToResourceDetails ->
                navigateToResourceDetails()

            AssignmentContract.Event.OnNavigateToResourceCreate ->
                navigateToResourceCreate()
        }
    }

    private fun setClassId(classId: String) {
        _state.update {
            it.copy(classId = classId)
        }
    }

    private fun fetchAssignments() {
        viewModelScope.launch {
            getAssignmentsUseCase(classId = _state.value.classId).asResult().collect { result ->
                result.onLoading {
                    _state.update {
                        it.copy(
                            uiState = AssignmentUiState.Loading
                        )
                    }
                }.onSuccess { assignments ->
                    _state.update {
                        it.copy(
                            uiState = if (assignments.isNotEmpty()) {
                                AssignmentUiState.Success(assignments)
                            } else {
                                AssignmentUiState.SuccessEmpty
                            }
                        )
                    }
                }.onNoInternet { message ->
                    onErrorGetAssignments(message)
                }.onError { _, message ->
                    onErrorGetAssignments(message)
                }.onException { exception, message ->
                    Log.e("AssignmentViewModel", exception?.message.toString())
                    onErrorGetAssignments(message)
                }
            }
        }
    }

    private fun onErrorGetAssignments(message: String) {
        _state.update {
            it.copy(
                uiState = AssignmentUiState.Error(message)
            )
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _effect.emit(AssignmentContract.Effect.NavigateBack)
        }
    }

    private fun navigateToResourceDetails() {
        viewModelScope.launch {
            _effect.emit(
                AssignmentContract.Effect.NavigateToResourceDetails(
                    resourceTypeOrdinal = assignmentOrdinal
                )
            )
        }
    }

    private fun navigateToResourceCreate() {
        viewModelScope.launch {
            _effect.emit(
                AssignmentContract.Effect.NavigateToResourceCreate(
                    classId = _state.value.classId,
                    resourceTypeOrdinal = assignmentOrdinal,
                )
            )
        }
    }
}