package com.muammarahlnn.learnyscape.feature.camera

import android.graphics.Color
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseAlertDialog


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file CameraScreen, 27/09/2023 01.28 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun CameraRoute(
    onCameraPermissionDenied: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CameraScreen(
        onCameraPermissionDenied = onCameraPermissionDenied,
        modifier = modifier,
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun CameraScreen(
    onCameraPermissionDenied: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isCameraPermissionGranted by rememberSaveable {
        mutableStateOf(false)
    }
    val cameraPermissionState = rememberPermissionState(
        permission = android.Manifest.permission.CAMERA,
    ) { isGranted ->
        isCameraPermissionGranted = isGranted
        if (!isGranted) {
            onCameraPermissionDenied()
        }
    }
    isCameraPermissionGranted = when (cameraPermissionState.status) {
        PermissionStatus.Granted -> true
        is PermissionStatus.Denied -> false
    }

    if (isCameraPermissionGranted) {
        CameraContent(
            modifier = modifier,
        )
    } else {
        RequestCameraPermissionContent(
            onAllowedPermission = {
                cameraPermissionState.launchPermissionRequest()
            },
            onCanceledPermission = {
                onCameraPermissionDenied()
            },
        )
    }
}

@Composable
private fun CameraContent(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraController = remember { LifecycleCameraController(context) }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { padding ->
        AndroidView(
            factory = { context ->
                PreviewView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        MATCH_PARENT,
                        MATCH_PARENT
                    )
                    setBackgroundColor(Color.BLACK)
                    scaleType = PreviewView.ScaleType.FILL_START
                }.also { previewView ->
                    previewView.controller = cameraController
                    cameraController.bindToLifecycle(lifecycleOwner)
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        )
    }
}

@Composable
private fun RequestCameraPermissionContent(
    onAllowedPermission: () -> Unit,
    onCanceledPermission: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showRequestCameraPermissionDialog by rememberSaveable {
        mutableStateOf(true)
    }
    if (showRequestCameraPermissionDialog) {
        RequestCameraPermissionDialog(
            onAllowedPermission = {
                onAllowedPermission()
                showRequestCameraPermissionDialog = false
            },
            onCanceledPermission = {
                onCanceledPermission()
                showRequestCameraPermissionDialog = false
            },
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground)
    )
}

@Composable
private fun RequestCameraPermissionDialog(
    onAllowedPermission: () -> Unit,
    onCanceledPermission: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseAlertDialog(
        title = stringResource(id = R.string.request_camera_permission_dialog_title),
        dialogText = stringResource(id = R.string.request_camera_permission_dialog_content),
        onConfirm = onAllowedPermission,
        onDismiss = onCanceledPermission,
        onDismissRequest = {
            // do nothing, to prevent user to dismiss the dialog
            // by clicking outside the dialog or pressing the back button
        },
        confirmText = stringResource(id = R.string.request_camera_permission_dialog_confirm_text),
        dismissText = stringResource(id = R.string.request_camera_permission_dialog_dismiss_text),
        modifier = modifier,
    )
}