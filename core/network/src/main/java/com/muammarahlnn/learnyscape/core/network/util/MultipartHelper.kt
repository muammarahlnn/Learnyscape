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

fun String.convertToTextRequestBody(): RequestBody =
    this.toRequestBody("text/plain".toMediaType())

fun File.convertToImageRequestBody(): RequestBody =
    this.asRequestBody("image/*".toMediaTypeOrNull())

fun File.getMimeType(): String =
    MimeTypeMap.getFileExtensionFromUrl(this.toString())?.run {
        MimeTypeMap.getSingleton().getMimeTypeFromExtension(this.lowercase())
    } ?: "application/octet-stream"

fun createFormData(
    partName: String,
    fileName: String?,
    requestBody: RequestBody,
): MultipartBody.Part = MultipartBody.Part.createFormData(partName, fileName, requestBody)