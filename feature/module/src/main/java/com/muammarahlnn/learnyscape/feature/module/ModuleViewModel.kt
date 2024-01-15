package com.muammarahlnn.learnyscape.feature.module

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
 * @File ModuleViewModel, 14/01/2024 14.04
 */
class ModuleViewModel : ViewModel(), ModuleContract {

    private val _state = MutableStateFlow(ModuleContract.State())
    override val state: StateFlow<ModuleContract.State> = _state

    private val _effect = MutableSharedFlow<ModuleContract.Effect>()
    override val effect: SharedFlow<ModuleContract.Effect> = _effect

    private val moduleOrdinal = ClassResourceType.MODULE.ordinal

    override fun event(event: ModuleContract.Event) {
        when (event) {
            is ModuleContract.Event.SetClassId ->
                setClassId(event.classId)

            ModuleContract.Event.OnNavigateBack ->
                navigateBack()

            ModuleContract.Event.OnNavigateToResourceDetails ->
                navigateToResourceDetails()

            ModuleContract.Event.OnNavigateToResourceCreate ->
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
            _effect.emit(ModuleContract.Effect.NavigateBack)
        }
    }

    private fun navigateToResourceDetails() {
        viewModelScope.launch {
            _effect.emit(
                ModuleContract.Effect.NavigateToResourceDetails(
                    resourceTypeOrdinal = moduleOrdinal
                )
            )
        }
    }

    private fun navigateToResourceCreate() {
        viewModelScope.launch {
            _effect.emit(
                ModuleContract.Effect.NavigateToResourceCreate(
                    classId = _state.value.classId,
                    resourceTypeOrdinal = moduleOrdinal,
                )
            )
        }
    }
}