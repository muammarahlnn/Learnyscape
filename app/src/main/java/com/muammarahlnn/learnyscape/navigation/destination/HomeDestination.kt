package com.muammarahlnn.learnyscape.navigation.destination

import com.muammarahlnn.learnyscape.feature.home.navigation.HOME_ROUTE
import com.muammarahlnn.learnyscape.feature.profile.navigation.PROFILE_ROUTE
import com.muammarahlnn.learnyscape.feature.schedule.navigation.SCHEDULE_ROUTE
import com.muammarahlnn.learnyscape.feature.search.navigation.SEARCH_ROUTE
import com.muammarahlnn.learnyscape.feature.home.R as homeR
import com.muammarahlnn.learnyscape.feature.search.R as searchR
import com.muammarahlnn.learnyscape.feature.schedule.R as scheduleR
import com.muammarahlnn.learnyscape.feature.profile.R as profileR


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeDestination, 12/09/2023 18.40 by Muammar Ahlan Abimanyu
 */
enum class HomeDestination(
    val route: String,
    val selectedIconId: Int,
    val unselectedIconId: Int,
) {
    HOME(
        route = HOME_ROUTE,
        selectedIconId = homeR.drawable.ic_home,
        unselectedIconId = homeR.drawable.ic_home_border
    ),
    SEARCH(
        route = SEARCH_ROUTE,
        selectedIconId = searchR.drawable.ic_search,
        unselectedIconId = searchR.drawable.ic_search_border,
    ),
    SCHEDULE(
        route = SCHEDULE_ROUTE,
        selectedIconId = scheduleR.drawable.ic_calendar_today,
        unselectedIconId = scheduleR.drawable.ic_calendary_today_border,
    ),
    PROFILE(
        route = PROFILE_ROUTE,
        selectedIconId = profileR.drawable.ic_person,
        unselectedIconId = profileR.drawable.ic_person_border,
    ),
}