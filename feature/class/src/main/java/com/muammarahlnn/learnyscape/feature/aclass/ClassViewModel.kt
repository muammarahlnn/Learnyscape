package com.muammarahlnn.learnyscape.feature.aclass

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muammarahlnn.learnyscape.core.common.result.asResult
import com.muammarahlnn.learnyscape.core.common.result.onError
import com.muammarahlnn.learnyscape.core.common.result.onException
import com.muammarahlnn.learnyscape.core.common.result.onLoading
import com.muammarahlnn.learnyscape.core.common.result.onNoInternet
import com.muammarahlnn.learnyscape.core.common.result.onSuccess
import com.muammarahlnn.learnyscape.core.domain.profile.GetProfilePicUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
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
) : ViewModel(), ClassContract {

    private val _state = MutableStateFlow(ClassContract.State())
    override val state: StateFlow<ClassContract.State> = _state

    private val _effect = MutableSharedFlow<ClassContract.Effect>()
    override val effect: SharedFlow<ClassContract.Effect> = _effect

    override fun event(event: ClassContract.Event) {
        when (event) {
            ClassContract.Event.FetchProfilePic -> fetchProfilePic()
        }
    }

    private fun fetchProfilePic() {
        viewModelScope.launch {
            getProfilePicUseCase().asResult().collect { result ->
                result.onLoading {
                    _state.update {
                        it.copy(isProfilePicLoading = true)
                    }
                }.onSuccess { profilePic ->
                    _state.update {
                        it.copy(
                            isProfilePicLoading = false,
                            profilePic = profilePic,
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
            it.copy(isProfilePicLoading = false)
        }
    }

    companion object {

        private const val TAG = "ClassViewModel"
    }
}