package com.muammarahlnn.learnyscape.feature.member

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
import com.muammarahlnn.learnyscape.core.domain.classmembers.GetClassMembersUseCase
import com.muammarahlnn.learnyscape.core.domain.profile.GetProfilePicByUrlUeCase
import com.muammarahlnn.learnyscape.core.model.data.EnrolledClassMembersModel
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
    private val getProfilePicByUrlUeCase: GetProfilePicByUrlUeCase,
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
                                uiState = MemberContract.UiState.Success,
                                lecturers = enrolledClassMembers.lecturers.map { lecturer ->
                                    lecturer.toClassMemberState()
                                },
                                students = enrolledClassMembers.students.map { student ->
                                    student.toClassMemberState()
                                },
                            )
                        }
                        fetchProfilePics()
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

    private fun EnrolledClassMembersModel.ClassMember.toClassMemberState(): MemberContract.ClassMemberState =
        MemberContract.ClassMemberState(
            name = name,
            profilePicUrl = profilePicUrl,
        )

    private fun fetchProfilePics() {
        viewModelScope.launch {
            state.value.lecturers.forEachIndexed { index, lecturer ->
                getProfilePicByUrlUeCase(lecturer.profilePicUrl)
                    .asResult()
                    .collect { result ->
                        handleFetchProfilePicLecturerResult(result, index)
                    }
            }

            state.value.students.forEachIndexed { index, student ->
                getProfilePicByUrlUeCase(student.profilePicUrl)
                    .asResult()
                    .collect { result ->
                        handleFetchProfilePicStudentResult(result, index)
                    }
            }
        }
    }

    private fun handleFetchProfilePicLecturerResult(
        result: Result<Bitmap?>,
        index: Int,
    ) {
        result.onLoading {
            _state.update {
                it.copy(
                    lecturers = it.lecturers.toMutableList().apply {
                        this[index] = this[index].copy(
                            profilePicUiState = MemberContract.ProfilePicUiState.Loading,
                        )
                    }.toList()
                )
            }
        }.onSuccess { profilePic ->
            _state.update {
                it.copy(
                    lecturers = it.lecturers.toMutableList().apply {
                        this[index] = this[index].copy(
                            profilePicUiState = MemberContract.ProfilePicUiState.Success(profilePic),
                        )
                    }.toList()
                )
            }
        }.onNoInternet { message ->
            onErrorFetchProfilePicLecturer(message, index)
        }.onError { _, message ->
            onErrorFetchProfilePicLecturer(message, index)
        }.onException { exception, _ ->
            onErrorFetchProfilePicLecturer(
                exception?.message.toString(),
                index
            )
        }
    }

    private fun onErrorFetchProfilePicLecturer(
        message: String,
        index: Int,
    ) {
        Log.e(TAG, message)
        _state.update {
            it.copy(
                lecturers = it.lecturers.toMutableList().apply {
                    this[index] = this[index].copy(
                        profilePicUiState = MemberContract.ProfilePicUiState.Success(null)
                    )
                }.toList()
            )
        }
    }

    private fun handleFetchProfilePicStudentResult(
        result: Result<Bitmap?>,
        index: Int,
    ) {
        result.onLoading {
            _state.update {
                it.copy(
                    students = it.students.toMutableList().apply {
                        this[index] = this[index].copy(
                            profilePicUiState = MemberContract.ProfilePicUiState.Loading,
                        )
                    }.toList()
                )
            }
        }.onSuccess { profilePic ->
            _state.update {
                it.copy(
                    students = it.students.toMutableList().apply {
                        this[index] = this[index].copy(
                            profilePicUiState = MemberContract.ProfilePicUiState.Success(profilePic),
                        )
                    }.toList()
                )
            }
        }.onNoInternet { message ->
            onErrorFetchProfilePicStudent(message, index)
        }.onError { _, message ->
            onErrorFetchProfilePicStudent(message, index)
        }.onException { exception, _ ->
            onErrorFetchProfilePicStudent(
                exception?.message.toString(),
                index
            )
        }
    }

    private fun onErrorFetchProfilePicStudent(
        message: String,
        index: Int,
    ) {
        Log.e(TAG, message)
        _state.update {
            it.copy(
                students = it.students.toMutableList().apply {
                    this[index] = this[index].copy(
                        profilePicUiState = MemberContract.ProfilePicUiState.Success(null)
                    )
                }.toList()
            )
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

    companion object {

        private const val TAG = "MemberViewModel"
    }
}