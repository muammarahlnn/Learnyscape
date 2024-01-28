package com.muammarahlnn.learnyscape.feature.aclass

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.result.Result
import com.muammarahlnn.learnyscape.core.common.result.asResult
import com.muammarahlnn.learnyscape.core.common.result.onError
import com.muammarahlnn.learnyscape.core.common.result.onException
import com.muammarahlnn.learnyscape.core.common.result.onLoading
import com.muammarahlnn.learnyscape.core.common.result.onNoInternet
import com.muammarahlnn.learnyscape.core.common.result.onSuccess
import com.muammarahlnn.learnyscape.core.domain.classfeed.GetClassFeedsUseCase
import com.muammarahlnn.learnyscape.core.domain.profile.GetProfilePicByUrlUeCase
import com.muammarahlnn.learnyscape.core.domain.profile.GetProfilePicUseCase
import com.muammarahlnn.learnyscape.core.model.data.ClassFeedModel
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImageUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassViewModel, 15/12/2023 04.17
 */
@HiltViewModel
class ClassViewModel @Inject constructor(
    private val getProfilePicUseCase: GetProfilePicUseCase,
    private val getClassFeedsUseCase: GetClassFeedsUseCase,
    private val getProfilePicByUrlUeCase: GetProfilePicByUrlUeCase,
) : ViewModel(), ClassContract {

    private val _state = MutableStateFlow(ClassContract.State())
    override val state: StateFlow<ClassContract.State> = _state

    private val _effect = MutableSharedFlow<ClassContract.Effect>()
    override val effect: SharedFlow<ClassContract.Effect> = _effect

    private val _refreshing = MutableStateFlow(false)
    override val refreshing: StateFlow<Boolean> = _refreshing

    private val announcementOrdinal = ClassResourceType.ANNOUNCEMENT.ordinal

    override fun event(event: ClassContract.Event) {
        when (event) {
            is ClassContract.Event.SetClassId ->
                setClassId(event.classId)

            is ClassContract.Event.FetchClassFeeds ->
                fetchClassFeeds()

            ClassContract.Event.FetchProfilePic ->
                fetchProfilePic()

            ClassContract.Event.OnNavigateBack ->
                navigateBack()

            ClassContract.Event.OnNavigateToJoinRequests ->
                navigateToJoinRequests()

            ClassContract.Event.OnNavigateToResourceCreate ->
                navigateToResourceCreate()

            is ClassContract.Event.OnNavigateToResourceDetails ->
                navigateToResourceDetails()
        }
    }

    private fun setClassId(classId: String) {
        _state.update {
            it.copy(classId = classId)
        }
    }

    private fun fetchClassFeeds() {
        fun onErrorFetchClassFeeds(message: String) {
            _state.update {
                it.copy(
                    uiState = ClassContract.UiState.Error(message)
                )
            }
        }

        viewModelScope.launch {
            getClassFeedsUseCase(state.value.classId).asResult().collect { result ->
                result.onLoading {
                    _state.update {
                        it.copy(
                            uiState = ClassContract.UiState.Loading
                        )
                    }
                }.onSuccess { classFeeds ->
                    _state.update {
                        it.copy(
                            uiState = ClassContract.UiState.Success(classFeeds)
                        )
                    }
                    fetchAnnouncementAuthorProfilePics(classFeeds)
                }.onNoInternet { message ->
                    onErrorFetchClassFeeds(message)
                }.onError { _, message ->
                    onErrorFetchClassFeeds(message)
                }.onException { exception, message ->
                    Log.e(TAG, exception?.message.toString())
                    onErrorFetchClassFeeds(message)
                }
            }
        }
    }

    private fun fetchAnnouncementAuthorProfilePics(classFeeds: List<ClassFeedModel>) {
        viewModelScope.launch {
            classFeeds
                .filter { classFeed ->
                    classFeed.profilePicUrl.isNotEmpty()
                }
                .forEachIndexed { index, classFeed ->
                    getProfilePicByUrlUeCase(classFeed.profilePicUrl).asResult().collect { result ->
                        handleFetchAnnouncementAuthorProfilePic(result, index)
                    }
                }
        }
    }

    private fun handleFetchAnnouncementAuthorProfilePic(
        result: Result<Bitmap?>,
        index: Int,
    ) {
        fun onErrorHandleFetchAnnouncementAuthorProfilePic(message: String) {
            Log.e(TAG, message)
            _state.update {
                it.copy(
                    announcementAuthorProfilePicUiStateMap =
                    it.announcementAuthorProfilePicUiStateMap.toMutableMap().apply {
                        this[index] = PhotoProfileImageUiState.Success(null)
                    }.toMap()
                )
            }
        }

        result.onLoading {
            _state.update {
                it.copy(
                    announcementAuthorProfilePicUiStateMap =
                    it.announcementAuthorProfilePicUiStateMap.toMutableMap().apply {
                        this[index] = PhotoProfileImageUiState.Loading
                    }.toMap()
                )
            }
        }.onSuccess { profilePic ->
            _state.update {
                it.copy(
                    announcementAuthorProfilePicUiStateMap =
                    it.announcementAuthorProfilePicUiStateMap.toMutableMap().apply {
                        this[index] = PhotoProfileImageUiState.Success(profilePic)
                    }.toMap()
                )
            }
        }.onNoInternet { message ->
            onErrorHandleFetchAnnouncementAuthorProfilePic(message)
        }.onError { _, message ->
            onErrorHandleFetchAnnouncementAuthorProfilePic(message)
        }.onException { exception, _ ->
            onErrorHandleFetchAnnouncementAuthorProfilePic(exception?.message.toString())
        }
    }

    private fun fetchProfilePic() {
        viewModelScope.launch {
            getProfilePicUseCase().asResult().collect { result ->
                result.onLoading {
                    _state.update {
                        it.copy(
                            profilePicUiState = PhotoProfileImageUiState.Loading
                        )
                    }
                }.onSuccess { profilePic ->
                    _state.update {
                        it.copy(
                            profilePicUiState = PhotoProfileImageUiState.Success(profilePic)
                        )
                    }
                }.onNoInternet { message ->
                    _effect.emit(ClassContract.Effect.ShowToast(message))
                    updateIsProfilePicLoadingStateToFalse()
                }.onError { _, message ->
                    Log.e(TAG, message)
                    _effect.emit(ClassContract.Effect.ShowToast(message))
                    updateIsProfilePicLoadingStateToFalse()
                }.onException { exception, message ->
                    Log.e(TAG, exception?.message.toString())
                    _effect.emit(ClassContract.Effect.ShowToast(message))
                    updateIsProfilePicLoadingStateToFalse()
                }
            }
        }
    }

    private fun updateIsProfilePicLoadingStateToFalse() {
        _state.update {
            it.copy(
                profilePicUiState = PhotoProfileImageUiState.Success(null)
            )
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _effect.emit(ClassContract.Effect.NavigateBack)
        }
    }

    private fun navigateToJoinRequests() {
        viewModelScope.launch {
            _effect.emit(ClassContract.Effect.NavigateToJoinRequests(_state.value.classId))
        }
    }

    private fun navigateToResourceCreate() {
        viewModelScope.launch {
            _effect.emit(
                ClassContract.Effect.NavigateToResourceCreate(
                    classId = _state.value.classId,
                    resourceTypeOrdinal = announcementOrdinal,
                )
            )
        }
    }

    private fun navigateToResourceDetails() {
        viewModelScope.launch {
            _effect.emit(
                ClassContract.Effect.NavigateToResourceDetails(
                    resourceTypeOrdinal = announcementOrdinal
                )
            )
        }
    }

    companion object {

        private const val TAG = "ClassViewModel"
    }
}