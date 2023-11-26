package com.muammarahlnn.learnyscape.core.data.repository.impl

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import com.muammarahlnn.learnyscape.core.data.repository.FileRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file FileRepositoryImpl, 24/11/2023 15.46 by Muammar Ahlan Abimanyu
 */
class FileRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context,
) : FileRepository {

        override fun saveImageToFile(image: Bitmap): Flow<File?> = flow {
        val fileNameFormat = "dd-MMMM-yyyy"
        val timeStamp = SimpleDateFormat(
            fileNameFormat,
            Locale.US
        ).format(System.currentTimeMillis())

        val appName = "Learnyscape"
        val fileDirectory = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            appName
        )
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs()
        }

        val imageFile = File(fileDirectory, "${timeStamp}.jpg")
        val saveToFile = try {
            val outputStream: OutputStream = FileOutputStream(imageFile)
            image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            imageFile
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
        emit(saveToFile)
    }.flowOn(Dispatchers.IO)
}