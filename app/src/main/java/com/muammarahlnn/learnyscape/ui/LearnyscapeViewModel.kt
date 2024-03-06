package com.muammarahlnn.learnyscape.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.domain.login.IsUserLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File LearnyscapeViewModel, 06/03/2024 21.30
 */
@HiltViewModel
class LearnyscapeViewModel @Inject constructor(
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase
) : ViewModel() {

    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn = _isUserLoggedIn.asStateFlow()

    fun checkIfUserLoggedIn() {
        viewModelScope.launch {
            isUserLoggedInUseCase().collect { isUserLoggedIn ->
                _isUserLoggedIn.update { isUserLoggedIn }
            }
        }
    }
}