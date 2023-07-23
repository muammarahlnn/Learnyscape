package com.muammarahlnn.learnyscape.feature.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.profile.ProfileScreen


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ProfileNavigation, 20/07/2023 21.46 by Muammar Ahlan Abimanyu
 */

const val profileRoute = "profile_route"

fun NavController.navigateToProfile(navOptions: NavOptions? = null) {
    this.navigate(profileRoute, navOptions)
}

fun NavGraphBuilder.profileScreen() {
    composable(route = profileRoute) {
        ProfileScreen()
    }
}