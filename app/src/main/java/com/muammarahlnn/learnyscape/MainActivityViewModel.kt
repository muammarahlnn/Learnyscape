package com.muammarahlnn.learnyscape

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.domain.IsUserLoggedInUseCase
import com.muammarahlnn.learnyscape.core.model.data.NoParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file MainViewModel, 08/10/2023 21.28 by Muammar Ahlan Abimanyu
 */
@HiltViewModel
class MainActivityViewModel @Inject constructor(
    isUserLoggedIn: IsUserLoggedInUseCase,
) : ViewModel() {

    val mainActivityUiState: StateFlow<MainActivityUiState> =
        isUserLoggedIn.execute(NoParams).map { isLoggedIn ->
            MainActivityUiState.Success(isLoggedIn)
        }.stateIn(
            scope = viewModelScope,
            initialValue = MainActivityUiState.Loading,
            started = SharingStarted.WhileSubscribed(5_000),
        )
}