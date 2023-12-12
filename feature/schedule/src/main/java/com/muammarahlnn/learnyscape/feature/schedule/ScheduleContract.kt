package com.muammarahlnn.learnyscape.feature.schedule

import com.muammarahlnn.learnyscape.core.common.contract.BaseContract
import com.muammarahlnn.learnyscape.core.common.contract.RefreshProvider
import com.muammarahlnn.learnyscape.core.model.data.ScheduleModel

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ScheduleContract, 08/12/2023 23.06
 */
interface ScheduleContract :
    BaseContract<ScheduleContract.State, ScheduleContract.Event>,
    RefreshProvider {

    sealed interface State {

        data object Loading : State

        data class Success(val schedules: List<ScheduleModel>) : State

        data object SuccessEmpty : State

        data class NoInternet(val message: String) : State

        data class Error(val message: String) : State
    }

    sealed interface Event {

        data object OnGetSchedules : Event
    }
}