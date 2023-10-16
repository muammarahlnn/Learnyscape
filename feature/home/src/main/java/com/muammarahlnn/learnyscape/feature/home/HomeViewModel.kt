package com.muammarahlnn.learnyscape.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.result.map
import com.muammarahlnn.learnyscape.core.domain.GetClassesUseCase
import com.muammarahlnn.learnyscape.core.model.data.NoParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeViewModel, 12/10/2023 22.36 by Muammar Ahlan Abimanyu
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    getClasses: GetClassesUseCase,
) : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> = getClasses.execute(NoParams).map(
        onLoading = {
            HomeUiState.Loading
        },
        onSuccess = { classInfos ->
            if (classInfos.isNotEmpty()) {
                HomeUiState.Success(classInfos)
            } else {
                HomeUiState.SuccessEmptyClasses
            }
        },
        onError = { code, message ->
            Log.e("HomeViewModel", "code: $code - message: $message")
            HomeUiState.Error(message)
        },
        onException = { exception, message ->
            Log.e("HomeViewModel", exception?.message.toString())
            HomeUiState.Error(message)
        }
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState.Loading,
    )
}