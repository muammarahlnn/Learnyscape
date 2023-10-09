package com.muammarahlnn.learnyscape.core.ui.util

import androidx.compose.runtime.staticCompositionLocalOf
import com.muammarahlnn.learnyscape.core.model.data.UserModel


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file UiHelper, 10/10/2023 01.48 by Muammar Ahlan Abimanyu
 */

val LocalUserModel = staticCompositionLocalOf { UserModel() }