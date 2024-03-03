package com.muammarahlnn.learnyscape.core.network.datasource.impl

import com.muammarahlnn.learnyscape.core.network.api.UsersApi
import com.muammarahlnn.learnyscape.core.network.api.WaitingListApi
import com.muammarahlnn.learnyscape.core.network.datasource.PendingRequestNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.di.BEARER_TOKEN_AUTH
import com.muammarahlnn.learnyscape.core.network.model.request.RequestJoinClassRequest
import com.muammarahlnn.learnyscape.core.network.model.response.PendingRequestResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * @Author Muammar Ahlan Abimanyu
 * @File PendingRequestDataSourceImpl, 29/02/2024 22.01
 */
@Singleton
class PendingRequestNetworkDataSourceImpl @Inject constructor(
    private val waitingListApi: WaitingListApi,
    @Named(BEARER_TOKEN_AUTH) private val usersApi: UsersApi,
) : PendingRequestNetworkDataSource {

    override fun getStudentPendingRequestClasses(): Flow<List<PendingRequestResponse>> = flow {
        emit(waitingListApi.getStudentPendingRequestClasses().data)
    }

    override fun cancelStudentRequestClass(classId: String): Flow<String>  = flow {
        emit(
            usersApi.putRequestJoinClass(
                RequestJoinClassRequest(
                    classId = classId,
                    isCancelled = true,
                )
            ).data
        )
    }
}