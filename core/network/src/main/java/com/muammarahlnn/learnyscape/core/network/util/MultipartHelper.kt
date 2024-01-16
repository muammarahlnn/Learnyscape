package com.muammarahlnn.learnyscape.core.network.util

import android.webkit.MimeTypeMap
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File MultipartHelper, 13/01/2024 04.51
 */

fun String.toTextRequestBody(): RequestBody =
    this.toRequestBody("text/plain".toMediaType())

fun String.toTextRequestBodyOrNull(): RequestBody? = this.takeIf {
    it.isNotEmpty()
}?.toTextRequestBody()

fun File.toImageRequestBody(): RequestBody =
    this.asRequestBody("image/*".toMediaTypeOrNull())

fun File.getMimeType(): String =
    MimeTypeMap.getFileExtensionFromUrl(this.toString())?.run {
        MimeTypeMap.getSingleton().getMimeTypeFromExtension(this.lowercase())
    } ?: "application/octet-stream"

fun List<File>.toFileParts(partName: String): List<MultipartBody.Part> {
    val filesParts = mutableListOf<MultipartBody.Part>()
    this.forEach { file ->
        filesParts.add(
            createFormData(
                partName = partName,
                fileName = file.name,
                requestBody = file.asRequestBody(file.getMimeType().toMediaTypeOrNull())
            )
        )
    }
    return filesParts
}

fun createFormData(
    partName: String,
    fileName: String?,
    requestBody: RequestBody,
): MultipartBody.Part = MultipartBody.Part.createFormData(partName, fileName, requestBody)