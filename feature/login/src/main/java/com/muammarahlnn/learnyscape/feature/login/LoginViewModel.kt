package com.muammarahlnn.learnyscape.feature.login

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
import com.muammarahlnn.learnyscape.core.domain.login.PostLoginUserUseCase
import com.muammarahlnn.learnyscape.core.domain.login.SaveUserUseCase
import com.muammarahlnn.learnyscape.feature.login.LoginContract.Effect
import com.muammarahlnn.learnyscape.feature.login.LoginContract.Event
import com.muammarahlnn.learnyscape.feature.login.LoginContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file LoginViewModel, 02/10/2023 20.06 by Muammar Ahlan Abimanyu
 */

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val postLoginUserUseCase: PostLoginUserUseCase,
    private val saveUserUseCase: SaveUserUseCase
) : ViewModel(), ContractProvider<State, Event, Effect> by contract(State()) {

    override fun event(event: Event) {
        when (event) {
            is Event.OnUsernameChange -> onUsernameChange(event.username)
            is Event.OnPasswordChange -> onPasswordChange(event.password)
            Event.OnLoginButtonClick -> onUserLogin()
        }
    }

    private fun onUsernameChange(username: String) {
        updateState {
            it.copy(username = username)
        }
        enablingLoginButton()
    }

    private fun onPasswordChange(password: String) {
        updateState {
            it.copy(password = password)
        }
        enablingLoginButton()
    }

    private fun enablingLoginButton() {
        updateState {
            if (it.username.isNotBlank() && it.password.isNotBlank()) {
                it.copy(isLoginButtonEnabled = true)
            } else {
                it.copy(isLoginButtonEnabled = false)
            }
        }
    }

    private fun onUserLogin() {
        viewModelScope.launch {
            postLoginUserUseCase(
                username = state.value.username,
                password = state.value.password
            ).asResult().collect { result ->
                result.onLoading {
                    updateStateOnLoading()
                }.onSuccess { loginModel ->
                    saveUserUseCase(loginModel.accessToken).asResult().collect { result ->
                        result.onLoading {
                            updateStateOnLoading()
                        }.onSuccess { userModel ->
                            Log.d(
                                TAG,
                                "User logged in: ${userModel.fullName} role -> ${userModel.role.name}"
                            )

                            updateState {
                                it.copy(
                                    isLoginSuccess = true,
                                    loading = false,
                                    username = "",
                                    password = "",
                                )
                            }
                        }.onNoInternet { message ->
                            updateStateOnError(message)
                        }.onError { _, message ->
                            updateStateOnError(message)
                        }.onException { exception, message ->
                            updateStateOnError(message, exception)
                        }
                    }
                }.onNoInternet { message ->
                    updateStateOnError(message)
                }.onError { _, message ->
                    updateStateOnError(message)
                }.onException { exception, message ->
                    updateStateOnError(message, exception)
                }
            }
        }
    }

    private fun updateStateOnLoading() {
        updateState {
            it.copy(loading = true)
        }
    }

    private fun updateStateOnError(
        message: String,
        exception: Throwable? = null,
    ) {
        updateState {
            it.copy(loading = false)
        }

        Log.e(TAG, exception?.message ?: message)
        viewModelScope.emitEffect(Effect.ShowSnackbar(message))
    }

    companion object {

        private const val TAG = "ProfileViewModel"
    }
}