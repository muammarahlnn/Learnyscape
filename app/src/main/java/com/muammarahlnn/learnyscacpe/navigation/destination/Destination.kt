package com.muammarahlnn.learnyscacpe.navigation.destination

import androidx.annotation.DrawableRes
import com.muammarahlnn.learnyscape.feature.aclass.navigation.classRoute
import com.muammarahlnn.learnyscape.feature.assignment.navigation.assignmentRoute
import com.muammarahlnn.learnyscape.feature.home.navigation.homeRoute
import com.muammarahlnn.learnyscape.feature.member.navigation.memberRoute
import com.muammarahlnn.learnyscape.feature.module.navigation.moduleRoute
import com.muammarahlnn.learnyscape.feature.profile.navigation.profileRoute
import com.muammarahlnn.learnyscape.feature.quiz.navigation.quizRoute
import com.muammarahlnn.learnyscape.feature.schedule.navigation.scheduleRoute
import com.muammarahlnn.learnyscape.feature.search.navigation.searchRoute
import com.muammarahlnn.learnyscape.feature.aclass.R as classR
import com.muammarahlnn.learnyscape.feature.assignment.R as assignmentR
import com.muammarahlnn.learnyscape.feature.home.R as homeR
import com.muammarahlnn.learnyscape.feature.member.R as memberR
import com.muammarahlnn.learnyscape.feature.module.R as moduleR
import com.muammarahlnn.learnyscape.feature.profile.R as profileR
import com.muammarahlnn.learnyscape.feature.quiz.R as quizR
import com.muammarahlnn.learnyscape.feature.schedule.R as scheduleR
import com.muammarahlnn.learnyscape.feature.search.R as searchR


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file Destination, 07/08/2023 15.41 by Muammar Ahlan Abimanyu
 */

sealed interface Destination {

    val route: String

    @get:DrawableRes
    val selectedIconId: Int

    @get:DrawableRes
    val unselectedIconId: Int

    enum class TopLevelDestination(
        override val route: String,
        override val selectedIconId: Int,
        override val unselectedIconId: Int,
    ) : Destination {

        HOME(
            route = homeRoute,
            selectedIconId = homeR.drawable.ic_home,
            unselectedIconId = homeR.drawable.ic_home_border,
        ),
        SEARCH(
            route = searchRoute,
            selectedIconId = searchR.drawable.ic_search,
            unselectedIconId = searchR.drawable.ic_search,
        ),
        SCHEDULE(
            route = scheduleRoute,
            selectedIconId = scheduleR.drawable.ic_calendar_today,
            unselectedIconId = scheduleR.drawable.ic_calendary_today_border,
        ),
        PROFILE(
            route = profileRoute,
            selectedIconId = profileR.drawable.ic_person,
            unselectedIconId = profileR.drawable.ic_person_border,
        ),
    }

    enum class ClassDestination(
        override val route: String,
        override val selectedIconId: Int,
        override val unselectedIconId: Int,
    ) : Destination {

        CLASS(
            route = classRoute,
            selectedIconId = classR.drawable.ic_announcement,
            unselectedIconId = classR.drawable.ic_announcement_border,
        ),
        MODULE(
            route = moduleRoute,
            selectedIconId = moduleR.drawable.ic_book,
            unselectedIconId = moduleR.drawable.ic_book_border,
        ),
        ASSIGNMENT(
            route = assignmentRoute,
            selectedIconId = assignmentR.drawable.ic_assignment,
            unselectedIconId = assignmentR.drawable.ic_assignment_border,
        ),
        QUIZ(
            route = quizRoute,
            selectedIconId = quizR.drawable.ic_quiz,
            unselectedIconId = quizR.drawable.ic_quiz_border,
        ),
        MEMBER(
            route = memberRoute,
            selectedIconId = memberR.drawable.ic_group,
            unselectedIconId = memberR.drawable.ic_group_border,
        ),
    }
}