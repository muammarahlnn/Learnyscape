package com.muammarahlnn.learnyscape.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.result.Result
import com.muammarahlnn.learnyscape.core.domain.GetClassesUseCase
import com.muammarahlnn.learnyscape.core.model.data.NoParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
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

    val homeUiState: StateFlow<HomeUiState> =
        getClasses.execute(NoParams).map { result ->
            when (result) {
                Result.Loading -> HomeUiState.Loading
                is Result.Success -> {
                    if (result.data.isNotEmpty()) {
                        HomeUiState.Success(result.data)
                    } else {
                        HomeUiState.SuccessEmptyClasses
                    }
                }
                is Result.Error -> HomeUiState.Error(result.message)
                is Result.Exception -> HomeUiState.Error("System is busy, please try again later")
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading,
        )
}