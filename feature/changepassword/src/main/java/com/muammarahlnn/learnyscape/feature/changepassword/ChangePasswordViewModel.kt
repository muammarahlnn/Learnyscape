package com.muammarahlnn.learnyscape.feature.changepassword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ChangePasswordViewModel, 14/11/2023 11.53 by Muammar Ahlan Abimanyu
 */
class ChangePasswordViewModel : ViewModel() {

    var oldPassword by mutableStateOf("")
        private set

    var newPassword by mutableStateOf("")
        private set

    var confirmNewPassword by mutableStateOf("")
        private set

    fun setOldPasswordValue(oldPassword: String) {
        this.oldPassword = oldPassword
    }

    fun setNewPasswordValue(newPassword: String) {
        this.newPassword = newPassword
    }

    fun setConfirmNewPasswordValue(confirmNewPassword: String) {
        this.confirmNewPassword = confirmNewPassword
    }
}