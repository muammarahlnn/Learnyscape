package com.muammarahlnn.learnyscape.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.muammarahlnn.learnyscape.core.domain.GetLoggedInUserUseCase
import com.muammarahlnn.learnyscape.core.model.data.NoParams
import com.muammarahlnn.learnyscape.core.model.data.UserModel
import com.muammarahlnn.learnyscape.feature.camera.navigation.CAMERA_ROUTE
import com.muammarahlnn.learnyscape.feature.classnavigator.navigation.CLASS_NAVIGATOR_ROUTE
import com.muammarahlnn.learnyscape.feature.login.navigation.LOGIN_ROUTE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file LearnyscapeAppState, 20/07/2023 17.50 by Muammar Ahlan Abimanyu
 */

@Composable
fun rememberLearnyscapeAppState(
    getLoggedInUser: GetLoggedInUserUseCase,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): LearnyscapeAppState {
    return remember(
        getLoggedInUser,
        navController,
        coroutineScope,
    ) {
        LearnyscapeAppState(
            navController,
            getLoggedInUser,
            coroutineScope,
        )
    }
}

@Stable
class LearnyscapeAppState(
    val navController: NavHostController,
    getLoggedInUser: GetLoggedInUserUseCase,
    coroutineScope: CoroutineScope,
) {

    val user = getLoggedInUser.execute(NoParams)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UserModel()
        )

    private val currentDestination: NavDestination?
        @Composable
        get() = navController.currentBackStackEntryAsState().value?.destination

    private val currentRoute: String?
        @Composable
        get() = currentDestination?.route

    val currentStatusBarColor: Color
        @Composable
        get() = when (currentRoute) {
            CLASS_NAVIGATOR_ROUTE -> MaterialTheme.colorScheme.onPrimary
            LOGIN_ROUTE -> MaterialTheme.colorScheme.background
            CAMERA_ROUTE -> Color.Black
            else -> MaterialTheme.colorScheme.primary
        }
}