package com.muammarahlnn.learnyscape.core.data.repository.impl

import com.muammarahlnn.learnyscape.core.data.mapper.toAssignmentSubmissionModel
import com.muammarahlnn.learnyscape.core.data.repository.SubmissionDetailsRepository
import com.muammarahlnn.learnyscape.core.model.data.AssignmentSubmissionModel
import com.muammarahlnn.learnyscape.core.network.datasource.AttachmentNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.SubmissionDetailsNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SubmissionDetailsRepositoryImpl, 13/02/2024 19.44
 */
class SubmissionDetailsRepositoryImpl @Inject constructor(
    private val submissionDetailsNetworkDataSource: SubmissionDetailsNetworkDataSource,
    private val attachmentNetworkDataSource: AttachmentNetworkDataSource,
) : SubmissionDetailsRepository {

    override fun getAssignmentSubmissionDetails(submissionId: String): Flow<AssignmentSubmissionModel> =
        submissionDetailsNetworkDataSource.getTaskSubmissionDetails(submissionId)
            .flatMapLatest { studentTaskSubmissionResponse ->
                attachmentNetworkDataSource.getAttachments(studentTaskSubmissionResponse.attachmentUrls)
                    .map { attachments ->
                        studentTaskSubmissionResponse.toAssignmentSubmissionModel(
                            attachments.filterNotNull()
                        )
                    }
            }
}