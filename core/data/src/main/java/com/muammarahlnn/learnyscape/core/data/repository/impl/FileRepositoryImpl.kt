package com.muammarahlnn.learnyscape.core.data.repository.impl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import com.muammarahlnn.learnyscape.core.data.repository.FileRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
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
            reduceFileImage(imageFile)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
        emit(saveToFile)
    }.flowOn(Dispatchers.IO)

    private fun reduceFileImage(file: File): File {
        val bitmap = BitmapFactory.decodeFile(file.path)
        var compressQuality = 100
        var streamLength: Int
        do {
            val bitmapStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bitmapStream)

            val bitmapPicByteArray = bitmapStream.toByteArray()
            streamLength = bitmapPicByteArray.size
            compressQuality -= 5
        } while (streamLength > 1000000)
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
        return file
    }
}