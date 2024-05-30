package com.muammarahlnn.learnyscape.core.testing.repository

import com.muammarahlnn.learnyscape.core.data.repository.HomeRepository
import com.muammarahlnn.learnyscape.core.model.data.EnrolledClassInfoModel
import com.muammarahlnn.learnyscape.core.model.data.UserModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import org.jetbrains.annotations.TestOnly

/**
 * @Author Muammar Ahlan Abimanyu
 * @File TestHomeRepository, 30/05/2024 17.07
 */
class TestHomeRepository : HomeRepository {

    private val userModelFlow: MutableSharedFlow<UserModel> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val enrolledClassesInfoFlow: MutableSharedFlow<List<EnrolledClassInfoModel>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun getLoggedInUser(): Flow<UserModel> = userModelFlow

    override fun getEnrolledClasses(): Flow<List<EnrolledClassInfoModel>> = enrolledClassesInfoFlow

    @TestOnly
    fun sendEnrolledClasses(enrolledClasses: List<EnrolledClassInfoModel>) {
        enrolledClassesInfoFlow.tryEmit(enrolledClasses)
    }
}