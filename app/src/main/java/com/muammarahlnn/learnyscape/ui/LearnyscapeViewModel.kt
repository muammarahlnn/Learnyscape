package com.muammarahlnn.learnyscape.ui

import androidx.lifecycle.ViewModel
import com.muammarahlnn.learnyscape.core.domain.login.IsUserLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File LearnyscapeViewModel, 06/03/2024 21.30
 */
@HiltViewModel
class LearnyscapeViewModel @Inject constructor(
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase
) : ViewModel() {

    fun isUserLoggedIn(): Boolean = isUserLoggedInUseCase()
}