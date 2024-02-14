package com.muammarahlnn.learnyscape.core.network.datasource

import com.muammarahlnn.learnyscape.core.network.model.response.StudentTaskSubmissionResponse
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SubmissionDetailsNetworkDataSource, 13/02/2024 19.38
 */
interface SubmissionDetailsNetworkDataSource {

    fun getTaskSubmissionDetails(submissionId: String): Flow<StudentTaskSubmissionResponse>
}