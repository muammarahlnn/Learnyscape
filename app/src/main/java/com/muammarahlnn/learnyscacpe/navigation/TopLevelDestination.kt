package com.muammarahlnn.learnyscacpe.navigation

import androidx.annotation.DrawableRes
import com.muammarahlnn.learnyscape.feature.home.R as homeR
import com.muammarahlnn.learnyscape.feature.profile.R as profileR
import com.muammarahlnn.learnyscape.feature.schedule.R as scheduleR
import com.muammarahlnn.learnyscape.feature.search.R as searchR


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file TopLevelDestination, 20/07/2023 11.51 by Muammar Ahlan Abimanyu
 */

enum class TopLevelDestination(
    @DrawableRes val selectedIconId: Int,
    @DrawableRes val unselectedIconId: Int,
    val iconTextId: Int,
) {
    HOME(
        selectedIconId = homeR.drawable.ic_home,
        unselectedIconId = homeR.drawable.ic_home_border,
        iconTextId = homeR.string.home
    ),
    SEARCH(
        selectedIconId = searchR.drawable.ic_search,
        unselectedIconId = searchR.drawable.ic_search_border,
        iconTextId = searchR.string.search
    ),
    SCHEDULE(
        selectedIconId = scheduleR.drawable.ic_calendar_today,
        unselectedIconId = scheduleR.drawable.ic_calendary_today_border,
        iconTextId = scheduleR.string.schedule
    ),
    PROFILE(
        selectedIconId = profileR.drawable.ic_person,
        unselectedIconId = profileR.drawable.ic_person_border,
        iconTextId = profileR.string.profile
    ),
}