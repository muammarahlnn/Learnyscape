package com.muammarahlnn.learnyscape.core.testing.datasource

import com.muammarahlnn.learnyscape.core.network.datasource.HomeNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.model.response.EnrolledClassInfoResponse
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import org.jetbrains.annotations.TestOnly

/**
 * @Author Muammar Ahlan Abimanyu
 * @File TestHomeNetworkDataSource, 31/05/2024 19.10
 */
class TestHomeNetworkDataSource : HomeNetworkDataSource {

    private val enrolledClassesFlow: MutableSharedFlow<List<EnrolledClassInfoResponse>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun getEnrolledClasses(): Flow<List<EnrolledClassInfoResponse>> = enrolledClassesFlow

    @TestOnly
    fun sendEnrolledClasses(enrolledClasses: List<EnrolledClassInfoResponse>) =
        enrolledClassesFlow.tryEmit(enrolledClasses)
}