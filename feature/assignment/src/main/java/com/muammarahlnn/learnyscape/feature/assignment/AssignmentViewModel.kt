package com.muammarahlnn.learnyscape.feature.assignment

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
import com.muammarahlnn.learnyscape.core.domain.resourceoverview.GetAssignmentsUseCase
import com.muammarahlnn.learnyscape.feature.assignment.AssignmentContract.Effect
import com.muammarahlnn.learnyscape.feature.assignment.AssignmentContract.Event
import com.muammarahlnn.learnyscape.feature.assignment.AssignmentContract.State
import com.muammarahlnn.learnyscape.feature.assignment.AssignmentContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AssignmentViewModel, 14/01/2024 14.55
 */
@HiltViewModel
class AssignmentViewModel @Inject constructor(
    private val getAssignmentsUseCase: GetAssignmentsUseCase,
) : ViewModel(),
    ContractProvider<State, Event, Effect> by contract(State()),
    RefreshProvider by refresh()
{

    override fun event(event: Event) {
        when (event) {
            is Event.SetClassId -> setClassId(event.classId)
            is Event.FetchAssignments -> fetchAssignments()
        }
    }

    private fun setClassId(classId: String) {
        updateState {
            it.copy(classId = classId)
        }
    }

    private fun fetchAssignments() {
        viewModelScope.launch {
            getAssignmentsUseCase(classId = state.value.classId).asResult().collect { result ->
                result.onLoading {
                    updateState {
                        it.copy(
                            uiState = UiState.Loading
                        )
                    }
                }.onSuccess { assignments ->
                    updateState {
                        it.copy(
                            uiState = if (assignments.isNotEmpty()) {
                                UiState.Success(assignments)
                            } else {
                                UiState.SuccessEmpty
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
        updateState {
            it.copy(
                uiState = UiState.Error(message)
            )
        }
    }
}