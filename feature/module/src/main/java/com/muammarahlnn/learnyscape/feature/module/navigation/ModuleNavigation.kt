package com.muammarahlnn.learnyscape.feature.module.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.module.ModuleRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ModuleNavigation, 03/08/2023 15.55 by Muammar Ahlan Abimanyu
 */
const val MODULE_ROUTE = "module_route"

fun NavController.navigateToModule(navOptions: NavOptions? = null) {
    this.navigate(MODULE_ROUTE, navOptions)
}

fun NavGraphBuilder.moduleScreen(
    classId: String,
    navigateBack: () -> Unit,
    navigateToResourceDetails: (Int) -> Unit,
    navigateToResourceCreate: (String, Int) -> Unit,
) {
    composable(route = MODULE_ROUTE) {
        ModuleRoute(
            classId = classId,
            navigateBack = navigateBack,
            navigateToResourceDetails = navigateToResourceDetails,
            navigateToResourceCreate = navigateToResourceCreate,
        )
    }
}