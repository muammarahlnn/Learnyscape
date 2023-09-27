package com.muammarahlnn.learnyscape.feature.camera.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.muammarahlnn.learnyscape.feature.camera.CameraRoute


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file CameraNavigation, 27/09/2023 02.11 by Muammar Ahlan Abimanyu
 */

const val CAMERA_ROUTE = "camera_route"

fun NavController.navigateToCamera(navOptions: NavOptions? = null) {
    this.navigate(CAMERA_ROUTE, navOptions)
}

fun NavGraphBuilder.cameraScreen(
    onCameraPermissionDenied: () -> Unit,
) {
    composable(route = CAMERA_ROUTE) {
        CameraRoute(
            onCameraPermissionDenied = onCameraPermissionDenied
        )
    }
}