package com.muammarahlnn.learnyscacpe.navigation.destination

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.muammarahlnn.learnyscape.feature.home.R as homeR
import com.muammarahlnn.learnyscape.feature.profile.R as profileR
import com.muammarahlnn.learnyscape.feature.schedule.R as scheduleR
import com.muammarahlnn.learnyscape.feature.search.R as searchR
import com.muammarahlnn.learnyscape.feature.aclass.R as classR
import com.muammarahlnn.learnyscape.feature.module.R as moduleR
import com.muammarahlnn.learnyscape.feature.assignment.R as assignmentR
import com.muammarahlnn.learnyscape.feature.quiz.R as quizR
import com.muammarahlnn.learnyscape.feature.member.R as memberR


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file Destination, 07/08/2023 15.41 by Muammar Ahlan Abimanyu
 */

sealed interface Destination {

    @get:StringRes
    val nameId: Int

    @get:DrawableRes
    val selectedIconId: Int

    @get:DrawableRes
    val unselectedIconId: Int

    enum class TopLevelDestination(
        override val nameId: Int,
        override val selectedIconId: Int,
        override val unselectedIconId: Int,
    ) : Destination {

        HOME(
            nameId = homeR.string.home,
            selectedIconId = homeR.drawable.ic_home,
            unselectedIconId = homeR.drawable.ic_home_border,
        ),
        SEARCH(
            nameId = searchR.string.search,
            selectedIconId = searchR.drawable.ic_search,
            unselectedIconId = searchR.drawable.ic_search,
        ),
        SCHEDULE(
            nameId = scheduleR.string.schedule,
            selectedIconId = scheduleR.drawable.ic_calendar_today,
            unselectedIconId = scheduleR.drawable.ic_calendary_today_border,
        ),
        PROFILE(
            nameId = profileR.string.profile,
            selectedIconId = profileR.drawable.ic_person,
            unselectedIconId = profileR.drawable.ic_person_border,
        ),
    }

    enum class ClassDestination(
        override val nameId: Int,
        override val selectedIconId: Int,
        override val unselectedIconId: Int,
    ) : Destination {

        CLASS(
            nameId = classR.string.announcement,
            selectedIconId = classR.drawable.ic_announcement,
            unselectedIconId = classR.drawable.ic_announcement_border,
        ),
        MODULE(
            nameId = moduleR.string.module,
            selectedIconId = moduleR.drawable.ic_book,
            unselectedIconId = moduleR.drawable.ic_book_border,
        ),
        ASSIGNMENT(
            nameId = assignmentR.string.assignment,
            selectedIconId = assignmentR.drawable.ic_assignment,
            unselectedIconId = assignmentR.drawable.ic_assignment_border,
        ),
        QUIZ(
            nameId = quizR.string.quiz,
            selectedIconId = quizR.drawable.ic_quiz,
            unselectedIconId = quizR.drawable.ic_quiz_border,
        ),
        MEMBER(
            nameId = memberR.string.member,
            selectedIconId = memberR.drawable.ic_group,
            unselectedIconId = memberR.drawable.ic_group_border,
        ),
    }
}