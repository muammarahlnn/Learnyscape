package com.muammarahlnn.learnyscape.core.network.datasource

import kotlinx.coroutines.flow.Flow
import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AttachmentNetworkDataSource, 13/02/2024 19.50
 */
interface AttachmentNetworkDataSource {

    fun getAttachments(attachmentUrls: List<String>): Flow<List<File?>>
}