package com.muammarahlnn.learnyscape.feature.profile.navigation

import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.profile.ProfileController
import com.muammarahlnn.learnyscape.feature.profile.ProfileRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ProfileNavigation, 20/07/2023 21.46 by Muammar Ahlan Abimanyu
 */

const val PROFILE_ROUTE = "profile_route"

fun NavController.navigateToProfile(navOptions: NavOptions? = null) {
    this.navigate(PROFILE_ROUTE, navOptions)
}

fun NavGraphBuilder.profileScreenn(
    navigateToCamera: () -> Unit,
    navigateToChangePassword: () -> Unit,
) {
    composable(route = PROFILE_ROUTE) {
        ProfileRoute(
            controller = ProfileController(
                scope = rememberCoroutineScope(),
                navigateToCamera = navigateToCamera,
                navigateToChangePassword = navigateToChangePassword,
            )
        )
    }
}