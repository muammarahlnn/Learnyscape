package com.muammarahlnn.learnyscape.feature.module

import com.muammarahlnn.learnyscape.core.model.data.ModuleOverviewModel
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ModuleContract, 14/01/2024 14.00
 */
interface ModuleContract  {

    data class State(
        val classId: String = "",
        val uiState: UiState = UiState.Loading,
        val moduleOrdinal: Int = ClassResourceType.MODULE.ordinal,
    )

    sealed interface UiState {

        data object Loading : UiState

        data class Success(val modules: List<ModuleOverviewModel>) : UiState

        data object SuccessEmpty : UiState

        data class Error(val message: String) : UiState
    }

    sealed interface Event {

        data class SetClassId(val classId: String) : Event

        data object FetchModules : Event
    }

    sealed interface Effect
}