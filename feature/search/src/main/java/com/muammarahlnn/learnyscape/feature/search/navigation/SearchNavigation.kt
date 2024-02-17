package com.muammarahlnn.learnyscape.feature.search.navigation

import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.search.SearchController
import com.muammarahlnn.learnyscape.feature.search.SearchRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file SearchNavigation, 20/07/2023 22.07 by Muammar Ahlan Abimanyu
 */

const val SEARCH_ROUTE = "search_route"

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    this.navigate(SEARCH_ROUTE, navOptions)
}

fun NavGraphBuilder.searchScreen(
    navigateToPendingRequestClass: () -> Unit,
) {
    composable(route = SEARCH_ROUTE) {
        SearchRoute(
            controller = SearchController(
                scope = rememberCoroutineScope(),
                navigateToPendingRequestClass = navigateToPendingRequestClass,
            )
        )
    }
}