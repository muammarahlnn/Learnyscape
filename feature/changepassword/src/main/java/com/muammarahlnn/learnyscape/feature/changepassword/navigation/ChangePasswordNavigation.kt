package com.muammarahlnn.learnyscape.feature.changepassword.navigation

import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.changepassword.ChangePasswordController
import com.muammarahlnn.learnyscape.feature.changepassword.ChangePasswordRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ChangePasswordNavigation, 14/11/2023 11.57 by Muammar Ahlan Abimanyu
 */
const val CHANGE_PASSWORD_ROUTE = "change_password_route"

fun NavController.navigateToChangePassword() {
    this.navigate(CHANGE_PASSWORD_ROUTE) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.changePasswordScreen(
    navigateBack: () -> Unit,
) {
    composable(CHANGE_PASSWORD_ROUTE) {
        ChangePasswordRoute(
            controller = ChangePasswordController(
                scope = rememberCoroutineScope(),
                navigateBack = navigateBack,
            )
        )
    }
}