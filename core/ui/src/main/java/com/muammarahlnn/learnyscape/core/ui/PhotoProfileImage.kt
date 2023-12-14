package com.muammarahlnn.learnyscape.core.ui

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage

/**
 * @Author Muammar Ahlan Abimanyu
 * @File PhotoProfileImage, 14/12/2023 23.23
 */
@Composable
fun PhotoProfileImage(
    photoProfile: Bitmap?,
    modifier: Modifier = Modifier,
) {
    if (photoProfile != null) {
        AsyncImage(
            model = photoProfile,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier,
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.dummy_photo_profile),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier,
        )
    }
}