package com.muammarahlnn.learnyscape.feature.aclass

import com.muammarahlnn.learnyscape.core.common.contract.BaseContract
import com.muammarahlnn.learnyscape.core.common.contract.EffectProvider
import com.muammarahlnn.learnyscape.core.common.contract.RefreshProvider
import com.muammarahlnn.learnyscape.core.model.data.ClassFeedModel
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImageUiState

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassContract, 15/12/2023 04.14
 */
interface ClassContract :
    BaseContract<ClassContract.State, ClassContract.Event>,
    EffectProvider<ClassContract.Effect>,
    RefreshProvider {

    data class State(
        val classId: String = "",
        val uiState: UiState = UiState.Loading,
        val profilePicUiState: PhotoProfileImageUiState = PhotoProfileImageUiState.Loading,
        val announcementAuthorProfilePicUiStateMap: Map<Int, PhotoProfileImageUiState> = mapOf(),
    )

    sealed interface UiState {

        data object Loading : UiState

        data class Success(val classFeeds: List<ClassFeedModel>) : UiState

        data class Error(val message: String) : UiState
    }

    sealed interface Event {

        data class SetClassId(val classId: String) : Event

        data object FetchProfilePic : Event

        data object FetchClassFeeds : Event

        data object OnNavigateBack : Event

        data object OnNavigateToJoinRequests : Event

        data object OnNavigateToResourceCreate : Event

        data class OnNavigateToResourceDetails(val resourceTypeOrdinal: Int) : Event
    }

    sealed interface Effect {

        data class ShowToast(val message: String) : Effect

        data object NavigateBack : Effect

        data class NavigateToJoinRequests(val classId: String) : Effect

        data class NavigateToResourceCreate(
            val classId: String,
            val resourceTypeOrdinal: Int,
        ) : Effect

        data class NavigateToResourceDetails(val resourceTypeOrdinal: Int) : Effect
    }
}