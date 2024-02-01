package com.muammarahlnn.learnyscape.feature.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.result.asResult
import com.muammarahlnn.learnyscape.core.common.result.onError
import com.muammarahlnn.learnyscape.core.common.result.onException
import com.muammarahlnn.learnyscape.core.common.result.onLoading
import com.muammarahlnn.learnyscape.core.common.result.onNoInternet
import com.muammarahlnn.learnyscape.core.common.result.onSuccess
import com.muammarahlnn.learnyscape.core.domain.login.PostLoginUserUseCase
import com.muammarahlnn.learnyscape.core.domain.login.SaveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
) : ViewModel(), LoginContract {

    private val _state = MutableStateFlow(LoginContract.State())
    override val state: StateFlow<LoginContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<LoginContract.Effect>()
    override val effect: SharedFlow<LoginContract.Effect> = _effect

    override fun event(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.OnUsernameChange -> onUsernameChange(event.username)
            is LoginContract.Event.OnPasswordChange -> onPasswordChange(event.password)
            LoginContract.Event.OnLoginButtonClick -> onUserLogin()
        }
    }

    private fun onUsernameChange(username: String) {
        _state.update {
            it.copy(username = username)
        }
        enablingLoginButton()
    }

    private fun onPasswordChange(password: String) {
        _state.update {
            it.copy(password = password)
        }
        enablingLoginButton()
    }

    private fun enablingLoginButton() {
        _state.update {
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
                            _state.update {
                                it.copy(
                                    username = "",
                                    password = "",
                                    loading = false
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
        _state.update {
            it.copy(loading = true)
        }
    }

    private fun updateStateOnError(
        message: String,
        exception: Throwable? = null,
    ) {
        _state.update {
            it.copy(loading = false)
        }

        Log.e(TAG, exception?.message ?: message)
        viewModelScope.launch {
            _effect.emit(
                LoginContract.Effect.ShowSnackbar(message)
            )
        }
    }

    companion object {

        private const val TAG = "ProfileViewModel"
    }
}