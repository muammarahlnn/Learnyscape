package com.muammarahlnn.learnyscape.feature.schedule.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.schedule.ScheduleRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ScheduleNavigation, 20/07/2023 21.51 by Muammar Ahlan Abimanyu
 */

const val SCHEDULE_ROUTE = "schedule_route"

fun NavController.navigateToSchedule(navOptions: NavOptions? = null) {
    this.navigate(SCHEDULE_ROUTE, navOptions)
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.scheduleScreen(
    scrollBehavior: TopAppBarScrollBehavior,
    onScheduleClick: () -> Unit,
) {
    composable(route = SCHEDULE_ROUTE) {
        ScheduleRoute(
            scrollBehavior = scrollBehavior,
            onScheduleClick = onScheduleClick
        )
    }
}