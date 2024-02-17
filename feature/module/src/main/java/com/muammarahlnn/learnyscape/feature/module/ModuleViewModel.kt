package com.muammarahlnn.learnyscape.feature.module

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
import com.muammarahlnn.learnyscape.core.domain.resourceoverview.GetModulesUseCase
import com.muammarahlnn.learnyscape.feature.module.ModuleContract.Effect
import com.muammarahlnn.learnyscape.feature.module.ModuleContract.Event
import com.muammarahlnn.learnyscape.feature.module.ModuleContract.State
import com.muammarahlnn.learnyscape.feature.module.ModuleContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ModuleViewModel, 14/01/2024 14.04
 */
@HiltViewModel
class ModuleViewModel @Inject constructor(
    private val getModulesUseCase: GetModulesUseCase,
) : ViewModel(),
    ContractProvider<State, Event, Effect> by contract(State()),
    RefreshProvider by refresh()
{

    override fun event(event: Event) {
        when (event) {
            is Event.SetClassId ->
                setClassId(event.classId)

            is Event.FetchModules ->
                fetchModules()
        }
    }

    private fun setClassId(classId: String) {
        updateState {
            it.copy(classId = classId)
        }
    }

    private fun fetchModules() {
        viewModelScope.launch {
            getModulesUseCase(classId = state.value.classId).asResult().collect { result ->
                result.onLoading {
                    updateState {
                        it.copy(
                            uiState = UiState.Loading
                        )
                    }
                }.onSuccess { modules ->
                    updateState {
                        it.copy(
                            uiState = if (modules.isNotEmpty()) {
                                UiState.Success(modules)
                            } else {
                                UiState.SuccessEmpty
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
        updateState {
            it.copy(
                uiState = UiState.Error(message)
            )
        }
    }
}