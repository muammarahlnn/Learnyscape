package com.muammarahlnn.learnyscacpe.navigation

import androidx.annotation.DrawableRes
import com.muammarahlnn.core.designsystem.icon.LearnyscapeIcons
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
        selectedIconId = LearnyscapeIcons.Home,
        unselectedIconId = LearnyscapeIcons.HomeBorder,
        iconTextId = homeR.string.home
    ),
    SEARCH(
        selectedIconId = LearnyscapeIcons.Search,
        unselectedIconId = LearnyscapeIcons.SearchBorder,
        iconTextId = searchR.string.search
    ),
    SCHEDULE(
        selectedIconId = LearnyscapeIcons.CalendarToday,
        unselectedIconId = LearnyscapeIcons.CalendarTodayBorder,
        iconTextId = scheduleR.string.schedule
    ),
    PROFILE(
        selectedIconId = LearnyscapeIcons.Person,
        unselectedIconId = LearnyscapeIcons.PersonBorder,
        iconTextId = profileR.string.profile
    ),
}