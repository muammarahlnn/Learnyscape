package com.muammarahlnn.learnyscape.feature.schedule.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.schedule.ScheduleScreen


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ScheduleNavigation, 20/07/2023 21.51 by Muammar Ahlan Abimanyu
 */

const val scheduleRoute = "schedule_route"

fun NavController.navigateToSchedule(navOptions: NavOptions? = null) {
    this.navigate(scheduleRoute, navOptions)
}

fun NavGraphBuilder.scheduleScreen() {
    composable(route = scheduleRoute) {
        ScheduleScreen()
    }
}