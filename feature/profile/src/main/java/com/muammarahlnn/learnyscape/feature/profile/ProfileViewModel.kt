package com.muammarahlnn.learnyscape.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.domain.profile.LogoutUseCase
import com.muammarahlnn.learnyscape.core.model.data.NoParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ProfileViewModel, 09/10/2023 21.37 by Muammar Ahlan Abimanyu
 */
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logout: LogoutUseCase,
) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            logout.execute(NoParams)
        }
    }
}