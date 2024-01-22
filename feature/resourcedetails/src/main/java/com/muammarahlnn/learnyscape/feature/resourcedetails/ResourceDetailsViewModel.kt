package com.muammarahlnn.learnyscape.feature.resourcedetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.result.asResult
import com.muammarahlnn.learnyscape.core.common.result.onError
import com.muammarahlnn.learnyscape.core.common.result.onException
import com.muammarahlnn.learnyscape.core.common.result.onLoading
import com.muammarahlnn.learnyscape.core.common.result.onNoInternet
import com.muammarahlnn.learnyscape.core.common.result.onSuccess
import com.muammarahlnn.learnyscape.core.domain.resourcedetails.GetModuleDetailsUseCase
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.feature.resourcedetails.navigation.ResourceDetailsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ResourceDetailsViewModel, 21/08/2023 21.10 by Muammar Ahlan Abimanyu
 */
@HiltViewModel
class ResourceDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getModuleDetailsUseCase: GetModuleDetailsUseCase,
) : ViewModel(), ResourceDetailsContract {

    private val resourceDetailsArgs = ResourceDetailsArgs(savedStateHandle)

    private val _state = MutableStateFlow(ResourceDetailsContract.State(
        resourceId = resourceDetailsArgs.resourceId,
        resourceType = ClassResourceType.getClassResourceType(resourceDetailsArgs.resourceTypeOrdinal)
    ))
    override val state: StateFlow<ResourceDetailsContract.State> = _state

    private val _effect = MutableSharedFlow<ResourceDetailsContract.Effect>()
    override val effect: SharedFlow<ResourceDetailsContract.Effect> = _effect

    private val _refreshing = MutableStateFlow(false)
    override val refreshing: StateFlow<Boolean> = _refreshing

    override fun event(event: ResourceDetailsContract.Event) {
        when (event) {
            ResourceDetailsContract.Event.FetchResourceDetails ->
                fetchResourceDetails()

            ResourceDetailsContract.Event.OnBackClick ->
                navigateBack()

            ResourceDetailsContract.Event.OnAddWorkButtonClick ->
                showAddWorkBottomSheet(true)

            ResourceDetailsContract.Event.OnCameraActionClick ->
                navigateToCamera()

            ResourceDetailsContract.Event.OnUploadFilesClick ->
                openFiles()

            ResourceDetailsContract.Event.OnDismissAddWorkBottomSheet ->
                showAddWorkBottomSheet(false)

            ResourceDetailsContract.Event.OnStartQuizButtonClick ->
                showStartQuizDialog(true)

            is ResourceDetailsContract.Event.OnConfirmStartQuizDialog ->
                navigateToQuizSession(
                    event.quizTypeOrdinal,
                    event.quizName,
                    event.quizDuration,
                )

            ResourceDetailsContract.Event.OnDismissStartQuizDialog ->
                showStartQuizDialog(false)
        }
    }

    private fun fetchResourceDetails() {
        viewModelScope.launch {
            getModuleDetailsUseCase(moduleId = _state.value.resourceId).asResult().collect { result ->
                result.onLoading {
                    _state.update {
                        it.copy(
                            uiState = ResourceDetailsContract.UiState.Loading
                        )
                    }
                }.onSuccess { moduleDetails ->
                    _state.update {
                        it.copy(
                            name = moduleDetails.name,
                            description = moduleDetails.description,
                            date = moduleDetails.updatedAt,
                            attachments = moduleDetails.attachments,
                            uiState = ResourceDetailsContract.UiState.Success,
                        )
                    }
                }.onNoInternet { message ->
                    onErrorFetchResourceDetails(message)
                }.onError { _, message ->
                    onErrorFetchResourceDetails(message)
                }.onException { exception, message ->
                    Log.e(TAG, exception?.message.toString())
                    onErrorFetchResourceDetails(message)
                }
            }
        }
    }

    private fun onErrorFetchResourceDetails(message: String) {
        _state.update {
            it.copy(
                uiState = ResourceDetailsContract.UiState.Error(message)
            )
        }
    }

    private fun showAddWorkBottomSheet(show: Boolean) {
        _state.update {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    showAddWorkBottomSheet = show
                )
            )
        }
    }

    private fun navigateToCamera() {
        viewModelScope.launch {
            _effect.emit(ResourceDetailsContract.Effect.NavigateToCamera)
        }
    }

    private fun openFiles() {
        viewModelScope.launch {
            _effect.emit(ResourceDetailsContract.Effect.OpenFiles)
        }
    }

    private fun showStartQuizDialog(show: Boolean) {
        _state.update {
            it.copy(
                overlayComposableVisibility = it.overlayComposableVisibility.copy(
                    showStartQuizDialog = show
                )
            )
        }
    }

    private fun navigateToQuizSession(
        quizTypeOrdinal: Int,
        quizName: String,
        quizDuration: Int,
    ) {
        viewModelScope.launch {
            _effect.emit(
                ResourceDetailsContract.Effect.NavigateToQuizSession(
                    quizTypeOrdinal = quizTypeOrdinal,
                    quizName = quizName,
                    quizDuration = quizDuration,
                )
            )
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _effect.emit(ResourceDetailsContract.Effect.NavigateBack)
        }
    }

    companion object {

        private const val TAG = "ResourceDetailsVM"
    }
}