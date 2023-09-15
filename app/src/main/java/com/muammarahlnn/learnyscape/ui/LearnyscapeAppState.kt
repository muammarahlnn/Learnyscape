package com.muammarahlnn.learnyscape.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file LearnyscapeAppState, 20/07/2023 17.50 by Muammar Ahlan Abimanyu
 */

@Composable
fun rememberLearnyscapeAppState(
    navController: NavHostController = rememberNavController(),
): LearnyscapeAppState {
    return remember(navController) {
        LearnyscapeAppState(navController)
    }
}

@Stable
class LearnyscapeAppState(
    val navController: NavHostController
)