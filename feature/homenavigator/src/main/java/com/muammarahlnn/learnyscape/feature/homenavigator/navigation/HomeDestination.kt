package com.muammarahlnn.learnyscape.feature.homenavigator.navigation

import com.muammarahlnn.learnyscape.feature.home.navigation.HOME_ROUTE
import com.muammarahlnn.learnyscape.feature.profile.navigation.PROFILE_ROUTE
import com.muammarahlnn.learnyscape.feature.schedule.navigation.SCHEDULE_ROUTE
import com.muammarahlnn.learnyscape.feature.search.navigation.SEARCH_ROUTE
import com.muammarahlnn.learnyscape.feature.home.R as homeR
import com.muammarahlnn.learnyscape.feature.search.R as searchR
import com.muammarahlnn.learnyscape.feature.schedule.R as scheduleR
import com.muammarahlnn.learnyscape.feature.profile.R as profileR
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR

/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeDestination, 15/09/2023 19.01 by Muammar Ahlan Abimanyu
 */
internal enum class HomeDestination(
    val route: String,
    val titleId: Int,
    val iconId: Int,
) {
    HOME(
        route = HOME_ROUTE,
        titleId = homeR.string.learnyscape,
        iconId = homeR.drawable.ic_home,
    ),
    SEARCH(
        route = SEARCH_ROUTE,
        titleId = searchR.string.available_class,
        iconId = designSystemR.drawable.ic_search,
    ),
    SCHEDULE(
        route = SCHEDULE_ROUTE,
        titleId = scheduleR.string.schedule,
        iconId = scheduleR.drawable.ic_calendar_today,
    ),
    PROFILE(
        route = PROFILE_ROUTE,
        titleId = profileR.string.profile,
        iconId = profileR.drawable.ic_person,
    ),
}