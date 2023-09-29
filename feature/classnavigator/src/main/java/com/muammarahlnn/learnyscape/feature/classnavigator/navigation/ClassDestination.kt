package com.muammarahlnn.learnyscape.feature.classnavigator.navigation

import com.muammarahlnn.learnyscape.feature.aclass.navigation.CLASS_ROUTE
import com.muammarahlnn.learnyscape.feature.assignment.navigation.ASSIGNMENT_ROUTE
import com.muammarahlnn.learnyscape.feature.member.navigation.MEMBER_ROUTE
import com.muammarahlnn.learnyscape.feature.module.navigation.MODULE_ROUTE
import com.muammarahlnn.learnyscape.feature.quiz.navigation.QUIZ_ROUTE
import com.muammarahlnn.learnyscape.feature.aclass.R as classR
import com.muammarahlnn.learnyscape.feature.assignment.R as assignmentR
import com.muammarahlnn.learnyscape.feature.member.R as memberR
import com.muammarahlnn.learnyscape.feature.module.R as moduleR
import com.muammarahlnn.learnyscape.feature.quiz.R as quizR

/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ClassDestination, 15/09/2023 19.16 by Muammar Ahlan Abimanyu
 */
internal enum class ClassDestination(
    val route: String,
    val selectedIconId: Int,
    val unselectedIconId: Int,
) {
    CLASS(
        route = CLASS_ROUTE,
        selectedIconId = classR.drawable.ic_announcement,
        unselectedIconId = classR.drawable.ic_announcement_border,
    ),
    MODULE(
        route = MODULE_ROUTE,
        selectedIconId = moduleR.drawable.ic_book,
        unselectedIconId = moduleR.drawable.ic_book_border,
    ),
    ASSIGNMENT(
        route = ASSIGNMENT_ROUTE,
        selectedIconId = assignmentR.drawable.ic_assignment,
        unselectedIconId = assignmentR.drawable.ic_assignment_border,
    ),
    QUIZ(
        route = QUIZ_ROUTE,
        selectedIconId = quizR.drawable.ic_quiz,
        unselectedIconId = quizR.drawable.ic_quiz_border,
    ),
    MEMBER(
        route = MEMBER_ROUTE,
        selectedIconId = memberR.drawable.ic_group,
        unselectedIconId = memberR.drawable.ic_group_border,
    );

    override fun toString(): String =
        name.lowercase().replaceFirstChar { firstChar ->
            firstChar.uppercase()
        }
}