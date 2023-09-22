package com.muammarahlnn.learnyscape.feature.schedule.navigation

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

fun NavGraphBuilder.scheduleScreen(
    onClassClick: () -> Unit,
) {
    composable(route = SCHEDULE_ROUTE) {
        ScheduleRoute(
            onClassClick = onClassClick
        )
    }
}