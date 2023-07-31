package com.muammarahlnn.learnyscape.feature.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.search.SearchRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file SearchNavigation, 20/07/2023 22.07 by Muammar Ahlan Abimanyu
 */

const val searchRoute = "search_route"

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    this.navigate(searchRoute, navOptions)
}

fun NavGraphBuilder.searchScreen() {
    composable(route = searchRoute) {
        SearchRoute()
    }
}