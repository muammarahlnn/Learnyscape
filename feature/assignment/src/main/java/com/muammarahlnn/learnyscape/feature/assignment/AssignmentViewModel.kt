package com.muammarahlnn.learnyscape.feature.assignment

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
 * @File AssignmentViewModel, 14/01/2024 14.55
 */
class AssignmentViewModel : ViewModel(), AssignmentContract {

    private val _state = MutableStateFlow(AssignmentContract.State())
    override val state: StateFlow<AssignmentContract.State> = _state

    private val _effect = MutableSharedFlow<AssignmentContract.Effect>()
    override val effect: SharedFlow<AssignmentContract.Effect> = _effect

    private val assignmentOrdinal = ClassResourceType.ASSIGNMENT.ordinal

    override fun event(event: AssignmentContract.Event) {
        when (event) {
            is AssignmentContract.Event.SetClassId ->
                setClassId(event.classId)

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