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
    val titleId: Int,
    val iconId: Int,
) {
    CLASS(
        route = CLASS_ROUTE,
        titleId = classR.string.clazz,
        iconId = classR.drawable.ic_announcement,
    ),
    MODULE(
        route = MODULE_ROUTE,
        titleId = moduleR.string.module,
        iconId = moduleR.drawable.ic_book,
    ),
    ASSIGNMENT(
        route = ASSIGNMENT_ROUTE,
        titleId = assignmentR.string.assignment,
        iconId = assignmentR.drawable.ic_assignment,
    ),
    QUIZ(
        route = QUIZ_ROUTE,
        titleId = quizR.string.quiz,
        iconId = quizR.drawable.ic_quiz,
    ),
    MEMBER(
        route = MEMBER_ROUTE,
        titleId = memberR.string.member,
        iconId = memberR.drawable.ic_group,
    );
}