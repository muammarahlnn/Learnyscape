package com.muammarahlnn.learnyscape.feature.aclass

import com.muammarahlnn.learnyscape.core.model.data.ClassDetailsModel
import com.muammarahlnn.learnyscape.core.model.data.ClassFeedModel
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImageUiState

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassContract, 15/12/2023 04.14
 */
interface ClassContract {

    data class State(
        val classId: String = "",
        val uiState: UiState = UiState.Loading,
        val announcementOrdinal: Int = ClassResourceType.ANNOUNCEMENT.ordinal,
        val profilePicUiState: PhotoProfileImageUiState = PhotoProfileImageUiState.Loading,
        val announcementAuthorProfilePicUiStateMap: Map<Int, PhotoProfileImageUiState> = mapOf(),
    )

    sealed interface UiState {

        data object Loading : UiState

        data class Success(
            val classDetails: ClassDetailsModel,
            val classFeeds: List<ClassFeedModel>,
        ) : UiState

        data class Error(val message: String) : UiState
    }

    sealed interface Event {

        data class SetClassId(val classId: String) : Event

        data object FetchProfilePic : Event

        data object FetchClassFeeds : Event
    }

    sealed interface Effect {

        data class ShowToast(val message: String) : Effect
    }
}