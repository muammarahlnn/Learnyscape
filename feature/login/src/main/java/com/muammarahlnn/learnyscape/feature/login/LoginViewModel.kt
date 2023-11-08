package com.muammarahlnn.learnyscape.feature.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.result.Result
import com.muammarahlnn.learnyscape.core.common.result.asResult
import com.muammarahlnn.learnyscape.core.common.result.onError
import com.muammarahlnn.learnyscape.core.common.result.onException
import com.muammarahlnn.learnyscape.core.common.result.onLoading
import com.muammarahlnn.learnyscape.core.common.result.onNoInternet
import com.muammarahlnn.learnyscape.core.common.result.onSuccess
import com.muammarahlnn.learnyscape.core.domain.PostLoginUserUseCase
import com.muammarahlnn.learnyscape.core.domain.SaveUserUseCase
import com.muammarahlnn.learnyscape.core.model.data.LoginModel
import com.muammarahlnn.learnyscape.core.model.data.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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
    private val postLoginUser: PostLoginUserUseCase,
    private val saveUser: SaveUserUseCase
) : ViewModel() {

    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.None)

    val loginUiState = _loginUiState.asStateFlow()

    fun postLoginUser(
        username: String,
        password: String,
    ) {
        viewModelScope.launch {
            postLoginUser.execute(
                params = PostLoginUserUseCase.Params(
                    username = username,
                    password = password,
                )
            ).asResult().collect { result ->
                handlePostLoginResult(result)
            }
        }
    }

    private suspend fun handlePostLoginResult(result: Result<LoginModel>) {
        result.onLoading {
            onLoadingResult()
        }.onSuccess { loginModel ->
            val accessToken = loginModel.accessToken
            saveUser.execute(
                params = SaveUserUseCase.Params(
                    token = accessToken
                )
            ).asResult().collect { result ->
                handleSaveUserResult(result)
            }
        }.onNoInternet { message ->
            onNoInternetResult(message)
        }.onError { _, message ->
            onErrorResult(message)
        }.onException { exception, message ->
            onExceptionResult(exception, message)
        }
    }

    private fun handleSaveUserResult(result: Result<UserModel>) {
        result.onLoading {
            onLoadingResult()
        }.onSuccess { userModel ->
            Log.d(
                "LoginViewModel",
                "User logged in: ${userModel.fullName} role -> ${userModel.role.name}"
            )

            _loginUiState.update {
                LoginUiState.None
            }
        }.onNoInternet { message ->
            onNoInternetResult(message)
        }.onError { _, message ->
            onErrorResult(message)
        }.onException { exception, message ->
            onExceptionResult(exception, message)
        }
    }

    private fun onLoadingResult() {
        _loginUiState.update {
            LoginUiState.Loading
        }
    }

    private fun onNoInternetResult(message: String) {
        _loginUiState.update {
            LoginUiState.Error(message)
        }
    }

    private fun onErrorResult(message: String) {
        _loginUiState.update {
            LoginUiState.Error(message)
        }
    }

    private fun onExceptionResult(exception: Throwable?, message: String) {
        Log.e("LoginViewModel", exception?.message.toString())

        _loginUiState.update {
            LoginUiState.Error(message)
        }
    }
}