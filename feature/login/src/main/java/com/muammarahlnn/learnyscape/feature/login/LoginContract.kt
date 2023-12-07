package com.muammarahlnn.learnyscape.feature.login

import com.muammarahlnn.learnyscape.core.common.contract.BaseContract
import com.muammarahlnn.learnyscape.core.common.contract.EffectProvider

/**
 * @Author Muammar Ahlan Abimanyu
 * @File LoginContract, 07/12/2023 19.15
 */
interface LoginContract :
    BaseContract<LoginContract.State, LoginContract.Event>,
    EffectProvider<LoginContract.Effect> {

    data class State(
        val loading: Boolean = false,
        val username: String = "",
        val password: String = "",
        val isLoginButtonEnabled: Boolean = false,
    )

    sealed interface Event {

        data class OnUsernameChange(val username: String) : Event

        data class OnPasswordChange(val password: String) : Event

        data object OnLoginButtonClick : Event
    }

    sealed interface Effect {

        data class ShowSnackbar(val message: String) : Effect
    }
}