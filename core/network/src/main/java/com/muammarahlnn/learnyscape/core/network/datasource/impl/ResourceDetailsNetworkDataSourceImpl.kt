package com.muammarahlnn.learnyscape.core.network.datasource.impl

import android.content.Context
import android.os.Environment
import com.muammarahlnn.learnyscape.core.network.api.AttachmentApi
import com.muammarahlnn.learnyscape.core.network.api.ReferencesApi
import com.muammarahlnn.learnyscape.core.network.api.TasksApi
import com.muammarahlnn.learnyscape.core.network.datasource.ResourceDetailsNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.di.BASE_URL
import com.muammarahlnn.learnyscape.core.network.model.response.ReferenceDetailsResponse
import com.muammarahlnn.learnyscape.core.network.model.response.TaskDetailsResponse
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
 * @File ResourceDetailsNetworkDataSourceImpl, 18/01/2024 17.38
 */
@Singleton
class ResourceDetailsNetworkDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val referencesApi: ReferencesApi,
    private val tasksApi: TasksApi,
    private val attachmentApi: AttachmentApi,
) : ResourceDetailsNetworkDataSource {

    override fun getAttachment(attachmentUrl: String): Flow<File?> = flow {
        val fullUrl = BASE_URL + attachmentUrl
        val response = attachmentApi.getAttachment(fullUrl)
        emit(response.toAttachmentFile())
    }

    override fun getReferenceDetails(referenceId: String): Flow<ReferenceDetailsResponse> = flow {
        emit(referencesApi.getReferenceDetails(referenceId).data)
    }

    override fun deleteReference(referenceId: String): Flow<String> = flow {
        emit(referencesApi.deleteReference(referenceId).data)
    }

    override fun getTaskDetails(taskId: String): Flow<TaskDetailsResponse> = flow {
        emit(tasksApi.getTaskDetails(taskId).data)
    }

    override fun deleteTask(taskId: String): Flow<String> = flow {
        emit(tasksApi.deleteTask(taskId).data)
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