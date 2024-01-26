package com.muammarahlnn.learnyscape.feature.member

import com.muammarahlnn.learnyscape.core.common.contract.BaseContract
import com.muammarahlnn.learnyscape.core.common.contract.EffectProvider
import com.muammarahlnn.learnyscape.core.common.contract.RefreshProvider
import com.muammarahlnn.learnyscape.core.model.data.EnrolledClassMembersModel

/**
 * @Author Muammar Ahlan Abimanyu
 * @File MemberContract, 25/01/2024 22.21
 */
interface MemberContract :
    BaseContract<MemberContract.State, MemberContract.Event>,
    EffectProvider<MemberContract.Effect>,
    RefreshProvider {

    data class State(
        val classId: String = "",
        val uiState: UiState = UiState.Loading,
    )

    sealed interface UiState {

        data object Loading : UiState

        data class Success(val enrolledClassMembersModel: EnrolledClassMembersModel) : UiState

        data class Error(val message: String) : UiState
    }

    sealed interface Event {

        data class SetClassId(val classId: String) : Event

        data object FetchClassMembers : Event

        data object OnNavigateBack : Event
    }

    sealed interface Effect {

        data object NavigateBack : Effect
    }
}