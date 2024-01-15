package com.muammarahlnn.learnyscape.feature.classnavigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.muammarahlnn.learnyscape.feature.aclass.navigation.navigateToClass
import com.muammarahlnn.learnyscape.feature.assignment.navigation.navigateToAssignment
import com.muammarahlnn.learnyscape.feature.classnavigator.navigation.ClassDestination
import com.muammarahlnn.learnyscape.feature.member.navigation.navigateToMember
import com.muammarahlnn.learnyscape.feature.module.navigation.navigateToModule
import com.muammarahlnn.learnyscape.feature.quiz.navigation.navigateToQuiz


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ClassNavigatorState, 15/09/2023 19.12 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun rememberClassNavigatorState(
    classId: String,
    navController: NavHostController = rememberNavController()
): ClassNavigatorState = remember(classId, navController) {
    ClassNavigatorState(classId, navController)
}

@Stable
internal class ClassNavigatorState(
    val classId: String,
    val navController: NavHostController
) {

    val currentDestination: NavDestination?
        @Composable
        get() = navController.currentBackStackEntryAsState().value?.destination

    val classDestinations: List<ClassDestination> = ClassDestination.entries

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