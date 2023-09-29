package com.muammarahlnn.learnyscape.feature.classnavigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.muammarahlnn.learnyscape.feature.aclass.navigation.CLASS_ROUTE
import com.muammarahlnn.learnyscape.feature.aclass.navigation.navigateToClass
import com.muammarahlnn.learnyscape.feature.assignment.navigation.ASSIGNMENT_ROUTE
import com.muammarahlnn.learnyscape.feature.assignment.navigation.navigateToAssignment
import com.muammarahlnn.learnyscape.feature.classnavigator.navigation.ClassDestination
import com.muammarahlnn.learnyscape.feature.member.navigation.MEMBER_ROUTE
import com.muammarahlnn.learnyscape.feature.member.navigation.navigateToMember
import com.muammarahlnn.learnyscape.feature.module.navigation.MODULE_ROUTE
import com.muammarahlnn.learnyscape.feature.module.navigation.navigateToModule
import com.muammarahlnn.learnyscape.feature.quiz.navigation.QUIZ_ROUTE
import com.muammarahlnn.learnyscape.feature.quiz.navigation.navigateToQuiz


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ClassNavigatorState, 15/09/2023 19.12 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun rememberClassNavigatorState(
    navController: NavHostController = rememberNavController()
): ClassNavigatorState = remember(navController) {
    ClassNavigatorState(navController)
}

@Stable
internal class ClassNavigatorState(
    val navController: NavHostController
) {

    val currentDestination: NavDestination?
        @Composable
        get() = navController.currentBackStackEntryAsState().value?.destination

    val currentClassDestination: ClassDestination?
        @Composable
        get() = when (currentDestination?.route) {
            CLASS_ROUTE -> ClassDestination.CLASS
            MODULE_ROUTE -> ClassDestination.MODULE
            ASSIGNMENT_ROUTE -> ClassDestination.ASSIGNMENT
            QUIZ_ROUTE -> ClassDestination.QUIZ
            MEMBER_ROUTE -> ClassDestination.MEMBER
            else -> null
        }

    val classDestinations: List<ClassDestination> = ClassDestination.values().asList()

    fun navigateToClassDestination(classDestination: ClassDestination) {
        val navOptions = navOptions {
            popUpTo(classDestination.route)
            launchSingleTop = true
            restoreState = true
        }

        when (classDestination) {
            ClassDestination.CLASS -> navController.navigateToClass(navOptions)
            ClassDestination.MODULE -> navController.navigateToModule(navOptions)
            ClassDestination.ASSIGNMENT -> navController.navigateToAssignment(navOptions)
            ClassDestination.QUIZ -> navController.navigateToQuiz(navOptions)
            ClassDestination.MEMBER -> navController.navigateToMember(navOptions)
        }
    }
}