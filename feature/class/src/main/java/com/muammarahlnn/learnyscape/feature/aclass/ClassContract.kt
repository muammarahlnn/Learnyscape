package com.muammarahlnn.learnyscape.feature.aclass

import android.graphics.Bitmap
import com.muammarahlnn.learnyscape.core.common.contract.BaseContract
import com.muammarahlnn.learnyscape.core.common.contract.EffectProvider

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassContract, 15/12/2023 04.14
 */
interface ClassContract :
    BaseContract<ClassContract.State, ClassContract.Event>,
    EffectProvider<ClassContract.Effect> {

    data class State(
        val classId: String = "",
        val profilePic: Bitmap? = null,
        val isProfilePicLoading: Boolean = false,
    )

    sealed interface Event {

        data class SetClassId(val classId: String) : Event

        data object FetchProfilePic : Event

        data object OnNavigateBack : Event

        data object OnNavigateToJoinRequests : Event

        data object OnNavigateToResourceCreate : Event

        data class OnNavigateToResourceDetails(val resourceTypeOrdinal: Int) : Event
    }

    sealed interface Effect {

        data class ShowToast(val message: String) : Effect

        data object NavigateBack : Effect

        data class NavigateToJoinRequests(val classId: String) : Effect

        data class NavigateToResourceCreate(
            val classId: String,
            val resourceTypeOrdinal: Int,
        ) : Effect

        data class NavigateToResourceDetails(val resourceTypeOrdinal: Int) : Effect
    }
}