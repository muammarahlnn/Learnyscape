package com.muammarahlnn.learnyscape.feature.camera

import android.graphics.Bitmap
import android.graphics.Matrix


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file CameraHelper, 21/11/2023 19.35 by Muammar Ahlan Abimanyu
 */
fun rotateBitmap(bitmap: Bitmap, isBackCamera: Boolean = false): Bitmap {
    val matrix = Matrix()
    return if (isBackCamera) {
        matrix.postRotate(90f)
        Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    } else {
        matrix.postRotate(-90f)
        matrix.postScale(-1f, 1f, bitmap.width / 2f, bitmap.height / 2f)
        Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    }
}