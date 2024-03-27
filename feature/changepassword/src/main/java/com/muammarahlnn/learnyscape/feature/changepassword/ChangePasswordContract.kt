package com.muammarahlnn.learnyscape.feature.changepassword

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ChangePasswordContract, 27/03/2024 15.44
 */
interface ChangePasswordContract {

    data class State(
        val oldPassword: String = "",
        val newPassword: String = "",
        val confirmNewPassword: String = "",
        val isLoading: Boolean = false,
    )

    sealed interface Event {

        data class OnOldPasswordChange(val value: String) : Event

        data class OnNewPasswordChange(val value: String) : Event

        data class OnConfirmNewPasswordChange(val value: String) : Event

        data object OnChangePasswordButtonClick : Event
    }

    sealed interface Effect {

        data class ShowToast(val message: String) : Effect

        data object OnSuccessChangePassword : Effect
    }
}