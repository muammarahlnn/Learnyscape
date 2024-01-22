package com.muammarahlnn.learnyscape.core.network.datasource.impl

import android.content.Context
import android.os.Environment
import android.webkit.MimeTypeMap
import com.muammarahlnn.learnyscape.core.network.api.ReferencesApi
import com.muammarahlnn.learnyscape.core.network.datasource.ResourceDetailsNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.di.BASE_URL
import com.muammarahlnn.learnyscape.core.network.model.response.ReferenceDetailsResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceDetailsNetworkDataSourceImpl, 18/01/2024 17.38
 */
@Singleton
class ResourceDetailsNetworkDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val referencesApi: ReferencesApi,
) : ResourceDetailsNetworkDataSource {

    override fun getReferenceDetails(referenceId: String): Flow<ReferenceDetailsResponse> = flow {
        emit(referencesApi.getReferenceDetails(referenceId).data)
    }

    override fun getReferenceAttachment(attachmentUrl: String): Flow<File?> = flow {
        val fullUrl = BASE_URL + attachmentUrl
        val response = referencesApi.getReferenceAttachment(fullUrl)
        if (response.isSuccessful) {
            val attachmentInputStream = response.body()?.byteStream()
            val attachmentContentType = response.headers().get("Content-Type")
            if (attachmentInputStream != null && attachmentContentType != null) {
                val attachmentFile = createFile(attachmentInputStream, attachmentContentType)
                emit(attachmentFile)
            } else {
                emit(null)
            }
        } else {
            emit(null)
        }
    }

    private fun createFile(inputStream: InputStream, mimeType: String): File {
        val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
        val dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val file =  File.createTempFile("Attachment", ".$extension", dir)

        val outputStream = FileOutputStream(file)
        val buf = ByteArray(1024)

        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) {
            outputStream.write(buf, 0, len)
        }
        outputStream.close()
        inputStream.close()

        return file
    }
}