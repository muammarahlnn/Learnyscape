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
        val profilePic: Bitmap? = null,
        val isProfilePicLoading: Boolean = false,
    )

    sealed interface Event {

        data object FetchProfilePic : Event
    }

    sealed interface Effect {

        data class ShowToast(val message: String) : Effect
    }
}