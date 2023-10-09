package com.muammarahlnn.learnyscape.feature.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.profile.ProfileRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ProfileNavigation, 20/07/2023 21.46 by Muammar Ahlan Abimanyu
 */

const val PROFILE_ROUTE = "profile_route"

fun NavController.navigateToProfile(navOptions: NavOptions? = null) {
    this.navigate(PROFILE_ROUTE, navOptions)
}

fun NavGraphBuilder.profileScreen(
    onCameraActionClick: () -> Unit,
) {
    composable(route = PROFILE_ROUTE) {
        ProfileRoute(
            onCameraActionClick = onCameraActionClick,
        )
    }
}