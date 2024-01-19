package com.muammarahlnn.learnyscape.feature.module

import com.muammarahlnn.learnyscape.core.common.contract.BaseContract
import com.muammarahlnn.learnyscape.core.common.contract.EffectProvider
import com.muammarahlnn.learnyscape.core.common.contract.RefreshProvider
import com.muammarahlnn.learnyscape.core.model.data.ModuleOverviewModel

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ModuleContract, 14/01/2024 14.00
 */
interface ModuleContract :
    BaseContract<ModuleContract.State, ModuleContract.Event>,
    EffectProvider<ModuleContract.Effect>,
    RefreshProvider {

    data class State(
        val classId: String = "",
        val uiState: ModuleUiState = ModuleUiState.Loading,
    )

    sealed interface Event {

        data class SetClassId(val classId: String) : Event

        data object FetchModules : Event

        data object OnNavigateBack : Event

        data class OnNavigateToResourceDetails(val moduleId: String) : Event

        data object OnNavigateToResourceCreate : Event
    }

    sealed interface Effect {

        data object NavigateBack : Effect

        data class NavigateToResourceDetails(
            val resourceId: String,
            val resourceTypeOrdinal: Int,
        ) : Effect

        data class NavigateToResourceCreate(
            val classId: String,
            val resourceTypeOrdinal: Int,
        ) : Effect
    }
}

sealed interface ModuleUiState {

    data object Loading : ModuleUiState

    data class Success(val modules: List<ModuleOverviewModel>) : ModuleUiState

    data object SuccessEmpty : ModuleUiState

    data class Error(val message: String) : ModuleUiState
}