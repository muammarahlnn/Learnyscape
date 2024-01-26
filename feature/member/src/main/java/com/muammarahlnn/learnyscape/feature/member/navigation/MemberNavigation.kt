package com.muammarahlnn.learnyscape.feature.member.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.member.MemberRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file MemberNavigation, 03/08/2023 15.54 by Muammar Ahlan Abimanyu
 */
const val MEMBER_ROUTE = "member_route"

fun NavController.navigateToMember(navOptions: NavOptions? = null) {
    this.navigate(MEMBER_ROUTE, navOptions)
}

fun NavGraphBuilder.memberScreen(
    classId: String,
    navigateBack: () -> Unit,
) {
    composable(route = MEMBER_ROUTE) {
        MemberRoute(
            classId = classId,
            navigateBack = navigateBack,
        )
    }
}