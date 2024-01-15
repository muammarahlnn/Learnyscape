package com.muammarahlnn.learnyscape.feature.module

import com.muammarahlnn.learnyscape.core.common.contract.BaseContract
import com.muammarahlnn.learnyscape.core.common.contract.EffectProvider

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ModuleContract, 14/01/2024 14.00
 */
interface ModuleContract :
    BaseContract<ModuleContract.State, ModuleContract.Event>,
    EffectProvider<ModuleContract.Effect> {

    data class State(
        val classId: String = ""
    )

    sealed interface Event {

        data class SetClassId(val classId: String) : Event

        data object OnNavigateBack : Event

        data object OnNavigateToResourceDetails : Event

        data object OnNavigateToResourceCreate : Event
    }

    sealed interface Effect {

        data object NavigateBack : Effect

        data class NavigateToResourceDetails(val resourceTypeOrdinal: Int) : Effect

        data class NavigateToResourceCreate(
            val classId: String,
            val resourceTypeOrdinal: Int,
        ) : Effect
    }
}