package com.muammarahlnn.learnyscape.feature.member

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
import com.muammarahlnn.learnyscape.core.domain.classmembers.GetClassMembersUseCase
import com.muammarahlnn.learnyscape.core.domain.profile.GetProfilePicByUrlUeCase
import com.muammarahlnn.learnyscape.core.model.data.EnrolledClassMembersModel
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImageUiState
import com.muammarahlnn.learnyscape.feature.member.MemberContract.ClassMemberState
import com.muammarahlnn.learnyscape.feature.member.MemberContract.Effect
import com.muammarahlnn.learnyscape.feature.member.MemberContract.Event
import com.muammarahlnn.learnyscape.feature.member.MemberContract.State
import com.muammarahlnn.learnyscape.feature.member.MemberContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
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
) : ViewModel(),
    ContractProvider<State, Event, Effect> by contract(State()),
    RefreshProvider by refresh()
{

    override fun event(event: Event) {
        when (event) {
            is Event.SetClassId ->setClassId(event.classId)
            Event.FetchClassMembers ->fetchClassMembers()
        }
    }

    private fun setClassId(classId: String) {
        updateState {
            it.copy(classId = classId)
        }
    }

    private fun fetchClassMembers() {
        viewModelScope.launch {
            getClassMembersUseCase(classId = state.value.classId)
                .asResult()
                .collect { result ->
                    result.onLoading {
                        updateState {
                            it.copy(
                                uiState = UiState.Loading,
                            )
                        }
                    }.onSuccess { enrolledClassMembers ->
                        updateState {
                            it.copy(
                                uiState = UiState.Success,
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

    private fun EnrolledClassMembersModel.ClassMember.toClassMemberState(): ClassMemberState =
        ClassMemberState(
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
            updateState {
                it.copy(
                    lecturers = it.lecturers.toMutableList().apply {
                        this[index] = this[index].copy(
                            profilePicUiState = PhotoProfileImageUiState.Loading,
                        )
                    }.toList()
                )
            }
        }.onSuccess { profilePic ->
            updateState {
                it.copy(
                    lecturers = it.lecturers.toMutableList().apply {
                        this[index] = this[index].copy(
                            profilePicUiState = PhotoProfileImageUiState.Success(profilePic),
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
        updateState {
            it.copy(
                lecturers = it.lecturers.toMutableList().apply {
                    this[index] = this[index].copy(
                        profilePicUiState = PhotoProfileImageUiState.Success(null)
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
            updateState {
                it.copy(
                    students = it.students.toMutableList().apply {
                        this[index] = this[index].copy(
                            profilePicUiState = PhotoProfileImageUiState.Loading,
                        )
                    }.toList()
                )
            }
        }.onSuccess { profilePic ->
            updateState {
                it.copy(
                    students = it.students.toMutableList().apply {
                        this[index] = this[index].copy(
                            profilePicUiState = PhotoProfileImageUiState.Success(profilePic),
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
        updateState {
            it.copy(
                students = it.students.toMutableList().apply {
                    this[index] = this[index].copy(
                        profilePicUiState = PhotoProfileImageUiState.Success(null)
                    )
                }.toList()
            )
        }
    }

    private fun onErrorFetchClassMembers(message: String) {
        updateState {
            it.copy(
                uiState = UiState.Error(message),
            )
        }
    }

    private companion object {

        const val TAG = "MemberViewModel"
    }
}