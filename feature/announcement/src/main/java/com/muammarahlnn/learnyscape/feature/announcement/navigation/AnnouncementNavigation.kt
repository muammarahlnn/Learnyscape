package com.muammarahlnn.learnyscape.feature.announcement.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.announcement.AnnouncementScreen


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file AnnouncementNavigation, 03/08/2023 15.52 by Muammar Ahlan Abimanyu
 */

const val announcementRoute = "announcement_route"

fun NavController.navigateToAnnouncement(navOptions: NavOptions? = null) {
    this.navigate(announcementRoute, navOptions)
}

fun NavGraphBuilder.announcementScreen() {
    composable(route = announcementRoute) {
        AnnouncementScreen()
    }
}