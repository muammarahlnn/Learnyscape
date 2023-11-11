package com.muammarahlnn.learnyscape.feature.search.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.search.SearchRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file SearchNavigation, 20/07/2023 22.07 by Muammar Ahlan Abimanyu
 */

const val SEARCH_ROUTE = "search_route"

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    this.navigate(SEARCH_ROUTE, navOptions)
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.searchScreen(
    scrollBehavior: TopAppBarScrollBehavior,
) {
    composable(route = SEARCH_ROUTE) {
        SearchRoute(
            scrollBehavior = scrollBehavior,
        )
    }
}