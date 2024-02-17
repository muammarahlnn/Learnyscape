package com.muammarahlnn.learnyscape.feature.schedule

import com.muammarahlnn.learnyscape.core.model.data.ScheduleModel

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ScheduleContract, 08/12/2023 23.06
 */
interface ScheduleContract {

    sealed interface State {

        data object Loading : State

        data class Success(val schedules: List<ScheduleModel>) : State

        data object SuccessEmpty : State

        data class NoInternet(val message: String) : State

        data class Error(val message: String) : State
    }

    sealed interface Event {

        data object FetchSchedules : Event
    }

    sealed interface Effect
}