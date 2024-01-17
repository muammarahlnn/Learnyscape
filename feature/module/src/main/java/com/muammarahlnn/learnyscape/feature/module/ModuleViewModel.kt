package com.muammarahlnn.learnyscape.feature.module

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.result.asResult
import com.muammarahlnn.learnyscape.core.common.result.onError
import com.muammarahlnn.learnyscape.core.common.result.onException
import com.muammarahlnn.learnyscape.core.common.result.onLoading
import com.muammarahlnn.learnyscape.core.common.result.onNoInternet
import com.muammarahlnn.learnyscape.core.common.result.onSuccess
import com.muammarahlnn.learnyscape.core.domain.resourceoverview.GetModulesUseCase
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
 * @File ModuleViewModel, 14/01/2024 14.04
 */
@HiltViewModel
class ModuleViewModel @Inject constructor(
    private val getModulesUseCase: GetModulesUseCase,
) : ViewModel(), ModuleContract {

    private val _state = MutableStateFlow(ModuleContract.State())
    override val state: StateFlow<ModuleContract.State> = _state

    private val _effect = MutableSharedFlow<ModuleContract.Effect>()
    override val effect: SharedFlow<ModuleContract.Effect> = _effect

    private val _refreshing = MutableStateFlow(false)
    override val refreshing: StateFlow<Boolean> = _refreshing

    private val moduleOrdinal = ClassResourceType.MODULE.ordinal

    override fun event(event: ModuleContract.Event) {
        when (event) {
            is ModuleContract.Event.SetClassId ->
                setClassId(event.classId)

            is ModuleContract.Event.FetchModules ->
                fetchModules()

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

    private fun fetchModules() {
        viewModelScope.launch {
            getModulesUseCase(classId = _state.value.classId).asResult().collect { result ->
                result.onLoading {
                    _state.update {
                        it.copy(
                            uiState = ModuleUiState.Loading
                        )
                    }
                }.onSuccess { modules ->
                    _state.update {
                        it.copy(
                            uiState = if (modules.isNotEmpty()) {
                                ModuleUiState.Success(modules)
                            } else {
                                ModuleUiState.SuccessEmpty
                            }
                        )
                    }
                }.onNoInternet { message ->
                    onErrorGetModules(message)
                }.onError { _, message ->
                    onErrorGetModules(message)
                }.onException { exception, message ->
                    Log.e("ModuleViewModel", exception?.message.toString())
                    onErrorGetModules(message)
                }
            }
        }
    }

    private fun onErrorGetModules(message: String) {
        _state.update {
            it.copy(
                uiState = ModuleUiState.Error(message)
            )
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