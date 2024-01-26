package com.muammarahlnn.learnyscape.feature.member

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.result.asResult
import com.muammarahlnn.learnyscape.core.common.result.onError
import com.muammarahlnn.learnyscape.core.common.result.onException
import com.muammarahlnn.learnyscape.core.common.result.onLoading
import com.muammarahlnn.learnyscape.core.common.result.onNoInternet
import com.muammarahlnn.learnyscape.core.common.result.onSuccess
import com.muammarahlnn.learnyscape.core.domain.classmembers.GetClassMembersUseCase
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
 * @File MemberViewModel, 26/01/2024 10.16
 */
@HiltViewModel
class MemberViewModel @Inject constructor(
    private val getClassMembersUseCase: GetClassMembersUseCase,
) : ViewModel(), MemberContract {

    private val _state = MutableStateFlow(MemberContract.State())
    override val state: StateFlow<MemberContract.State> = _state

    private val _effect = MutableSharedFlow<MemberContract.Effect>()
    override val effect: SharedFlow<MemberContract.Effect> = _effect

    private val _refreshing = MutableStateFlow(false)
    override val refreshing: StateFlow<Boolean> = _refreshing

    override fun event(event: MemberContract.Event) {
        when (event) {
            is MemberContract.Event.SetClassId ->
                setClassId(event.classId)

            MemberContract.Event.FetchClassMembers ->
                fetchClassMembers()

            MemberContract.Event.OnNavigateBack ->
                navigateBack()
        }
    }

    private fun setClassId(classId: String) {
        _state.update {
            it.copy(classId = classId)
        }
    }

    private fun fetchClassMembers() {
        viewModelScope.launch {
            getClassMembersUseCase(classId = state.value.classId)
                .asResult()
                .collect { result ->
                    result.onLoading {
                        _state.update {
                            it.copy(
                                uiState = MemberContract.UiState.Loading,
                            )
                        }
                    }.onSuccess { enrolledClassMembers ->
                        _state.update {
                            it.copy(
                                uiState = MemberContract.UiState.Success(enrolledClassMembers),
                            )
                        }
                    }.onNoInternet { message ->
                        onErrorFetchClassMembers(message)
                    }.onError { _, message ->
                        onErrorFetchClassMembers(message)
                    }.onException { exception, message ->
                        Log.e("MemberViewModel", exception?.message.toString())
                        onErrorFetchClassMembers(message)
                    }
                }
        }
    }

    private fun onErrorFetchClassMembers(message: String) {
        _state.update {
            it.copy(
                uiState = MemberContract.UiState.Error(message),
            )
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _effect.emit(MemberContract.Effect.NavigateBack)
        }
    }
}