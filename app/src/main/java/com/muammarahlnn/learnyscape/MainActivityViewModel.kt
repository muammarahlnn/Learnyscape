package com.muammarahlnn.learnyscape

import androidx.lifecycle.ViewModel
import com.muammarahlnn.learnyscape.core.domain.login.IsUserLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File MainActivityViewModel, 21/04/2024 19.07
 */
@HiltViewModel
internal class MainActivityViewModel @Inject constructor(
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainActivityUiState>(MainActivityUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun invokeIsUserLoggedInUseCase() {
        _uiState.update {
            MainActivityUiState.Success(isUserLoggedInUseCase())
        }
    }
}