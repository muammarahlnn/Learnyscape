package com.muammarahlnn.learnyscape.core.network.datasource.impl

import android.content.Context
import android.os.Environment
import com.muammarahlnn.learnyscape.core.network.api.AttachmentApi
import com.muammarahlnn.learnyscape.core.network.datasource.AttachmentNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.di.BASE_URL
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AttachmentNetworkDataSourceImpl, 13/02/2024 19.51
 */
@Singleton
class AttachmentNetworkDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val attachmentApi: AttachmentApi,
) : AttachmentNetworkDataSource {

    override fun getAttachments(attachmentUrls: List<String>): Flow<List<File?>> = flow {
        emit(
            attachmentUrls.map { url ->
                val fullUrl = BASE_URL + url
                attachmentApi.getAttachment(fullUrl).toAttachmentFile()
            }
        )
    }

    private fun Response<ResponseBody>.toAttachmentFile(): File? {
        if (!this.isSuccessful) return null

        val attachmentInputStream = this.body()?.byteStream()
        val attachmentName = this.headers().get("Content-Title")
        return if (attachmentInputStream != null && attachmentName != null) {
            createFile(attachmentInputStream, attachmentName)
        } else {
            null
        }
    }

    private fun createFile(
        inputStream: InputStream,
        fileName: String,
    ): File {
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val file =  File(storageDir, fileName)

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