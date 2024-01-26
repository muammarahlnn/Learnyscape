package com.muammarahlnn.learnyscape.core.ui

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.muammarahlnn.learnyscape.core.ui.util.shimmerEffect

/**
 * @Author Muammar Ahlan Abimanyu
 * @File PhotoProfileImage, 14/12/2023 23.23
 */

sealed interface PhotoProfileImageUiState {

    data object Loading : PhotoProfileImageUiState

    data class Success(val image: Bitmap?) : PhotoProfileImageUiState
}

@Composable
fun PhotoProfileImage(
    uiState: PhotoProfileImageUiState,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        PhotoProfileImageUiState.Loading -> Box(
            modifier = modifier.shimmerEffect()
        )

        is PhotoProfileImageUiState.Success -> {
            if (uiState.image != null) {
                AsyncImage(
                    model = uiState.image,
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
    }
}