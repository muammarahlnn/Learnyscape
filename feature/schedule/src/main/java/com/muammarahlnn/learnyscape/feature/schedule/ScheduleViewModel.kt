package com.muammarahlnn.learnyscape.feature.schedule

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.result.asResult
import com.muammarahlnn.learnyscape.core.common.result.onError
import com.muammarahlnn.learnyscape.core.common.result.onException
import com.muammarahlnn.learnyscape.core.common.result.onLoading
import com.muammarahlnn.learnyscape.core.common.result.onNoInternet
import com.muammarahlnn.learnyscape.core.common.result.onSuccess
import com.muammarahlnn.learnyscape.core.domain.schedules.GetSchedulesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ScheduleViewModel, 08/12/2023 23.00
 */
@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val getSchedulesUseCase: GetSchedulesUseCase,
) : ViewModel(), ScheduleContract {

    private val _state = MutableStateFlow<ScheduleContract.State>(ScheduleContract.State.Loading)
    override val state: StateFlow<ScheduleContract.State> = _state

    private val _refreshing = MutableStateFlow(false)
    override val refreshing: StateFlow<Boolean> = _refreshing


    override fun event(event: ScheduleContract.Event) = when (event) {
        ScheduleContract.Event.OnGetSchedules -> getSchedules()
    }

    private fun getSchedules() {
        viewModelScope.launch {
            getSchedulesUseCase().asResult().collect { result ->
                result.onLoading {
                    _state.update {
                        ScheduleContract.State.Loading
                    }
                }.onSuccess { schedules ->
                    _state.update {
                        if (schedules.isNotEmpty()) {
                            ScheduleContract.State.Success(schedules)
                        } else {
                            ScheduleContract.State.SuccessEmpty
                        }
                    }
                }.onNoInternet { message ->
                    _state.update {
                        ScheduleContract.State.NoInternet(message)
                    }
                }.onError { _, message ->
                    Log.e(TAG, message)
                    _state.update {
                        ScheduleContract.State.Error(message)
                    }
                }.onException { exception, message ->
                    Log.e(TAG, exception?.message.toString())
                    _state.update {
                        ScheduleContract.State.Error(message)
                    }
                }
            }
        }
    }

    companion object {

        private const val TAG = "ScheduleViewModel"
    }
}