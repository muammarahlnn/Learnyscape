package com.muammarahlnn.learnyscape.feature.changepassword

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.contract.ContractProvider
import com.muammarahlnn.learnyscape.core.common.contract.contract
import com.muammarahlnn.learnyscape.core.common.result.asResult
import com.muammarahlnn.learnyscape.core.common.result.onError
import com.muammarahlnn.learnyscape.core.common.result.onException
import com.muammarahlnn.learnyscape.core.common.result.onLoading
import com.muammarahlnn.learnyscape.core.common.result.onNoInternet
import com.muammarahlnn.learnyscape.core.common.result.onSuccess
import com.muammarahlnn.learnyscape.core.domain.changepassword.ChangePasswordUseCase
import com.muammarahlnn.learnyscape.feature.changepassword.ChangePasswordContract.Effect
import com.muammarahlnn.learnyscape.feature.changepassword.ChangePasswordContract.Event
import com.muammarahlnn.learnyscape.feature.changepassword.ChangePasswordContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ChangePasswordViewModel, 14/11/2023 11.53 by Muammar Ahlan Abimanyu
 */
@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase,
) : ViewModel(),
    ContractProvider<State, Event, Effect> by contract(State())
{

    override fun event(event: Event) {
        when (event) {
            is Event.OnOldPasswordChange -> onOldPasswordChange(event.value)
            is Event.OnNewPasswordChange -> onNewPasswordChange(event.value)
            is Event.OnConfirmNewPasswordChange -> onConfirmNewPasswordChange(event.value)
            Event.OnChangePasswordButtonClick -> changePassword()
        }
    }

    private fun onOldPasswordChange(value: String) {
        updateState {
            it.copy(oldPassword = value)
        }
    }

    private fun onNewPasswordChange(value: String) {
        updateState {
            it.copy(newPassword = value)
        }
    }

    private fun onConfirmNewPasswordChange(value: String) {
        updateState {
            it.copy(confirmNewPassword = value)
        }
    }

    private fun changePassword() {
        if (state.value.oldPassword.isEmpty()) {
            showToast("Old password can't be empty")
            return
        }
        if (state.value.newPassword.isEmpty()) {
            showToast("New password can't be empty")
            return
        }
        if (state.value.confirmNewPassword.isEmpty()) {
            showToast("Confirm new password can't be empty")
            return
        }
        if (state.value.newPassword != state.value.confirmNewPassword) {
            showToast("Confirm new password must be same with new password")
            return
        }

        viewModelScope.launch {
            changePasswordUseCase(
                oldPassword = state.value.oldPassword,
                newPassword = state.value.newPassword,
            )
                .asResult()
                .onCompletion {
                    updateState {
                        it.copy(isLoading = false)
                    }
                }
                .collect { result ->
                    result.onLoading {
                        updateState {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }.onSuccess {
                        showToast("Your password changed successfully")
                        emitEffect(Effect.OnSuccessChangePassword)
                    }.onNoInternet { message ->
                        showToast(message)
                    }.onError { _, message ->
                        showToast(message)
                    }.onException { exception, message ->
                        Log.e(TAG, exception?.message.toString())
                        showToast(message)
                    }
                }
        }
    }

    private fun showToast(message: String) {
        viewModelScope.emitEffect(Effect.ShowToast(message))
    }

    private companion object {

        const val TAG = "ChangePasswordVM"
    }
}