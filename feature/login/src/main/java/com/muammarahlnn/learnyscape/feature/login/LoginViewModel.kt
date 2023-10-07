package com.muammarahlnn.learnyscape.feature.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.result.Result
import com.muammarahlnn.learnyscape.core.domain.PostLoginUserUseCase
import com.muammarahlnn.learnyscape.core.domain.SaveAccessTokenUseCase
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
    private val saveAccessToken: SaveAccessTokenUseCase,
) : ViewModel() {

    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.None)

    val loginUiState = _loginUiState.asStateFlow()

    fun userLogin(
        username: String,
        password: String,
    ) {
        viewModelScope.launch {
            postLoginUser.execute(
                params = PostLoginUserUseCase.Params(
                    username = username,
                    password = password,
                )
            ).collect { result ->
                when (result) {
                    Result.Loading -> {
                        _loginUiState.update {
                            LoginUiState.Loading
                        }
                    }

                    is Result.Success -> {
                        _loginUiState.update {
                            LoginUiState.Success(result.data)
                        }
                    }

                    is Result.Error -> {
                        _loginUiState.update {
                            LoginUiState.Error(result.message)
                        }
                    }

                    is Result.Exception -> {
                        _loginUiState.update {
                            Log.e("LoginViewModel", result.exception?.message.toString())
                            LoginUiState.Error("System is busy, please try again later")
                        }
                    }
                }
            }
        }
    }

    fun saveAccessToken(accessToken: String) {
        viewModelScope.launch {
            saveAccessToken.execute(
                params = SaveAccessTokenUseCase.Params(
                    accessToken = accessToken,
                )
            )
        }
    }
}