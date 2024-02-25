package com.muammarahlnn.learnyscape.feature.aclass

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.contract.ContractProvider
import com.muammarahlnn.learnyscape.core.common.contract.RefreshProvider
import com.muammarahlnn.learnyscape.core.common.contract.contract
import com.muammarahlnn.learnyscape.core.common.contract.refresh
import com.muammarahlnn.learnyscape.core.common.result.Result
import com.muammarahlnn.learnyscape.core.common.result.asResult
import com.muammarahlnn.learnyscape.core.common.result.onError
import com.muammarahlnn.learnyscape.core.common.result.onException
import com.muammarahlnn.learnyscape.core.common.result.onLoading
import com.muammarahlnn.learnyscape.core.common.result.onNoInternet
import com.muammarahlnn.learnyscape.core.common.result.onSuccess
import com.muammarahlnn.learnyscape.core.domain.classfeed.GetClassDetailsUseCase
import com.muammarahlnn.learnyscape.core.domain.classfeed.GetClassFeedsUseCase
import com.muammarahlnn.learnyscape.core.domain.profile.GetProfilePicByUrlUeCase
import com.muammarahlnn.learnyscape.core.domain.profile.GetProfilePicUseCase
import com.muammarahlnn.learnyscape.core.model.data.ClassFeedModel
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImageUiState
import com.muammarahlnn.learnyscape.feature.aclass.ClassContract.Effect
import com.muammarahlnn.learnyscape.feature.aclass.ClassContract.Event
import com.muammarahlnn.learnyscape.feature.aclass.ClassContract.State
import com.muammarahlnn.learnyscape.feature.aclass.ClassContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
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
    private val getClassDetailsUseCase: GetClassDetailsUseCase,
) : ViewModel(),
    ContractProvider<State, Event, Effect> by contract(State()),
    RefreshProvider by refresh()
{

    override fun event(event: Event) {
        when (event) {
            is Event.SetClassId -> setClassId(event.classId)
            is Event.FetchClassFeeds -> fetchClassFeeds()
            Event.FetchProfilePic -> fetchProfilePic()
        }
    }

    private fun setClassId(classId: String) {
        updateState {
            it.copy(classId = classId)
        }
    }

    private fun fetchClassFeeds() {

        fun onErrorFetchClassFeeds(message: String) {
            updateState {
                it.copy(
                    uiState = UiState.Error(message)
                )
            }
        }

        viewModelScope.launch {
            combine(
                getClassDetailsUseCase(state.value.classId),
                getClassFeedsUseCase(state.value.classId),
                ::Pair
            ).asResult().collect { result ->
                result.onLoading {
                    updateState {
                        it.copy(
                            uiState = UiState.Loading
                        )
                    }
                }.onSuccess { resultPair ->
                    val (classDetails, classFeeds) = resultPair
                    updateState {
                        it.copy(
                            uiState = UiState.Success(
                                classDetails = classDetails,
                                classFeeds = classFeeds,
                            )
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
            updateState {
                it.copy(
                    announcementAuthorProfilePicUiStateMap =
                    it.announcementAuthorProfilePicUiStateMap.toMutableMap().apply {
                        this[index] = PhotoProfileImageUiState.Success(null)
                    }.toMap()
                )
            }
        }

        result.onLoading {
            updateState {
                it.copy(
                    announcementAuthorProfilePicUiStateMap =
                    it.announcementAuthorProfilePicUiStateMap.toMutableMap().apply {
                        this[index] = PhotoProfileImageUiState.Loading
                    }.toMap()
                )
            }
        }.onSuccess { profilePic ->
            updateState {
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
        fun onErrorFetchProfilePic(message: String) {
            showToast(message)
            updateState {
                it.copy(
                    profilePicUiState = PhotoProfileImageUiState.Success(null)
                )
            }
        }

        viewModelScope.launch {
            getProfilePicUseCase().asResult().collect { result ->
                result.onLoading {
                    updateState {
                        it.copy(
                            profilePicUiState = PhotoProfileImageUiState.Loading
                        )
                    }
                }.onSuccess { profilePic ->
                    updateState {
                        it.copy(
                            profilePicUiState = PhotoProfileImageUiState.Success(profilePic)
                        )
                    }
                }.onNoInternet { message ->
                    onErrorFetchProfilePic(message)
                }.onError { _, message ->
                    Log.e(TAG, message)
                    onErrorFetchProfilePic(message)
                }.onException { exception, message ->
                    Log.e(TAG, exception?.message.toString())
                    onErrorFetchProfilePic(message)
                }
            }
        }
    }

    private fun showToast(message: String) {
        viewModelScope.emitEffect(Effect.ShowToast(message))
    }

    companion object {

        private const val TAG = "ClassViewModel"
    }
}