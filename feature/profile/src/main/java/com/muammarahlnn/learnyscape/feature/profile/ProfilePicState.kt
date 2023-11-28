package com.muammarahlnn.learnyscape.feature.profile

import android.graphics.Bitmap


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ProfilePicUiState, 26/11/2023 17.19 by Muammar Ahlan Abimanyu
 */
data class ProfilePicState(
    val profilePic: Bitmap? = null,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)