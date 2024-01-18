package com.muammarahlnn.learnyscape.core.network.datasource.impl

import com.muammarahlnn.learnyscape.core.network.api.UsersApi
import com.muammarahlnn.learnyscape.core.network.api.WaitingListApi
import com.muammarahlnn.learnyscape.core.network.datasource.JoinRequestNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.di.BEARER_TOKEN_AUTH
import com.muammarahlnn.learnyscape.core.network.model.request.StudentAcceptanceRequest
import com.muammarahlnn.learnyscape.core.network.model.response.WaitingListResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * @Author Muammar Ahlan Abimanyu
 * @File JoinRequestNetworkDataSourceImpl, 17/01/2024 21.33
 */
@Singleton
class JoinRequestNetworkDataSourceImpl @Inject constructor(
    @Named(BEARER_TOKEN_AUTH) private val usersApi: UsersApi,
    private val waitingListApi: WaitingListApi,
) : JoinRequestNetworkDataSource {

    override fun getWaitingListClass(classId: String): Flow<List<WaitingListResponse>> = flow {
        emit(usersApi.getWaitingList(classId).data)
    }

    override fun putStudentAcceptance(
        studentId: String,
        studentAcceptanceRequest: StudentAcceptanceRequest
    ): Flow<String> = flow {
        emit(
            waitingListApi.putStudentAcceptance(
                studentId = studentId,
                studentAcceptanceRequest = studentAcceptanceRequest
            ).data
        )
    }
}