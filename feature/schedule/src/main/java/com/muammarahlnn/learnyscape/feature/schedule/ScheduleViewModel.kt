package com.muammarahlnn.learnyscape.feature.schedule

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.contract.ContractProvider
import com.muammarahlnn.learnyscape.core.common.contract.RefreshProvider
import com.muammarahlnn.learnyscape.core.common.contract.contract
import com.muammarahlnn.learnyscape.core.common.contract.refresh
import com.muammarahlnn.learnyscape.core.common.result.asResult
import com.muammarahlnn.learnyscape.core.common.result.onError
import com.muammarahlnn.learnyscape.core.common.result.onException
import com.muammarahlnn.learnyscape.core.common.result.onLoading
import com.muammarahlnn.learnyscape.core.common.result.onNoInternet
import com.muammarahlnn.learnyscape.core.common.result.onSuccess
import com.muammarahlnn.learnyscape.core.domain.schedules.GetSchedulesUseCase
import com.muammarahlnn.learnyscape.feature.schedule.ScheduleContract.Effect
import com.muammarahlnn.learnyscape.feature.schedule.ScheduleContract.Event
import com.muammarahlnn.learnyscape.feature.schedule.ScheduleContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ScheduleViewModel, 08/12/2023 23.00
 */
@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val getSchedulesUseCase: GetSchedulesUseCase,
) : ViewModel(),
    ContractProvider<State, Event, Effect> by contract(State.Loading),
    RefreshProvider by refresh()
{

    override fun event(event: Event) = when (event) {
        Event.FetchSchedules -> fetchSchedules()
    }

    private fun fetchSchedules() {
        viewModelScope.launch {
            getSchedulesUseCase().asResult().collect { result ->
                result.onLoading {
                    updateState {
                        State.Loading
                    }
                }.onSuccess { schedules ->
                    updateState {
                        if (schedules.isNotEmpty()) {
                            State.Success(schedules)
                        } else {
                            State.SuccessEmpty
                        }
                    }
                }.onNoInternet { message ->
                    updateState {
                        State.NoInternet(message)
                    }
                }.onError { _, message ->
                    Log.e(TAG, message)
                    updateState {
                        State.Error(message)
                    }
                }.onException { exception, message ->
                    Log.e(TAG, exception?.message.toString())
                    updateState {
                        State.Error(message)
                    }
                }
            }
        }
    }

    companion object {

        private const val TAG = "ScheduleViewModel"
    }
}