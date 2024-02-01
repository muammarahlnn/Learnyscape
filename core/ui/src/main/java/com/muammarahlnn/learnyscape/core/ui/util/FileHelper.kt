package com.muammarahlnn.learnyscape.core.ui.util

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Locale


/**
 * @Author Muammar Ahlan Abimanyu
 * @File FileHelper, 02/12/2023 20.50
 */

private const val FILENAME_FORMAT ="dd-MMMM-yyyy"
private val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

fun imageUriToFile(selectedImg: Uri, context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createCustomTempImageFile(context)

    val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
    val outputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)

    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) {
        outputStream.write(buf, 0, len)
    }
    outputStream.close()
    inputStream.close()

    return myFile
}

fun uriToFile(context: Context, selectedFileUri: Uri): File {
    val file = createFile(context, selectedFileUri)
    val contentResolver = context.contentResolver

    val inputStream = contentResolver.openInputStream(selectedFileUri) as InputStream
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

fun bitmapToFile(context: Context, bitmap: Bitmap): File {
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val imageFile = File(storageDir, "$timeStamp.jpg")

    val outputStream = FileOutputStream(imageFile)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    outputStream.flush()
    outputStream.close()

    return imageFile
}

fun createCustomTempImageFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun createFile(context: Context, uri: Uri): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
    val fileName = getFileName(context, uri) + getFileExtension(context, uri)
    return File(storageDir, fileName)
}

fun getFileExtension(context: Context, uri: Uri): String? {
    val contentResolver = context.contentResolver
    val mimeTypeMap = MimeTypeMap.getSingleton()
    val extension = mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
    val formattedExtension = extension?.let {
        if (!it.startsWith(".")) {
            ".$it"
        } else {
            it
        }
    }
    return formattedExtension
}

fun getFileName(context: Context, uri: Uri): String? {
    var result: String? = null
    if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor.use { it ->
            if (it != null && it.moveToFirst()) {
                result = it.getString(it.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
            }
        }
    }

    if (result == null) {
        result = uri.path
        val cut = result!!.lastIndexOf('/')
        if (cut != -1) {
            result = result!!.substring(cut + 1)
        }
    }

    return result
}

fun openFile(context: Context, file: File) {
    val fileUri = FileProvider.getUriForFile(
        context,
        context.applicationContext.packageName + ".provider",
        file
    )

    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(fileUri, context.contentResolver.getType(fileUri))
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    }
    context.startActivity(intent)
}
