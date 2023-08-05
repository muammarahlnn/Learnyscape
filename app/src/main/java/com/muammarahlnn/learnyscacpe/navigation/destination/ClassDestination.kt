package com.muammarahlnn.learnyscacpe.navigation.destination

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.muammarahlnn.learnyscape.feature.aclass.R as classR
import com.muammarahlnn.learnyscape.feature.module.R as moduleR
import com.muammarahlnn.learnyscape.feature.assignment.R as assignmentR
import com.muammarahlnn.learnyscape.feature.quiz.R as quizR
import com.muammarahlnn.learnyscape.feature.member.R as memberR


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ClassDestination, 03/08/2023 21.22 by Muammar Ahlan Abimanyu
 */

enum class ClassDestination(
    @DrawableRes val selectedIconId: Int,
    @DrawableRes val unselectedIconId: Int,
    @StringRes val iconTextId: Int,
) {
    CLASS(
        selectedIconId = classR.drawable.ic_announcement,
        unselectedIconId = classR.drawable.ic_announcement_border,
        iconTextId = classR.string.announcement,
    ),
    MODULE(
        selectedIconId = moduleR.drawable.ic_book,
        unselectedIconId = moduleR.drawable.ic_book_border,
        iconTextId = moduleR.string.module,
    ),
    ASSIGNMENT(
        selectedIconId = assignmentR.drawable.ic_assignment,
        unselectedIconId = assignmentR.drawable.ic_assignment_border,
        iconTextId = assignmentR.string.assignment,
    ),
    QUIZ(
        selectedIconId = quizR.drawable.ic_quiz,
        unselectedIconId = quizR.drawable.ic_quiz_border,
        iconTextId = quizR.string.quiz,
    ),
    MEMBER(
        selectedIconId = memberR.drawable.ic_group,
        unselectedIconId = memberR.drawable.ic_group_border,
        iconTextId = memberR.string.member,
    ),
}