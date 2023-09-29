package com.muammarahlnn.learnyscape.feature.camera

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseAlertDialog
import android.graphics.Color as androidColor
import androidx.compose.ui.graphics.Color as composeColor
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file CameraScreen, 27/09/2023 01.28 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun CameraRoute(
    onCameraClosed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CameraScreen(
        onCameraPermissionDenied = onCameraClosed,
        onCameraClosed = onCameraClosed,
        modifier = modifier,
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun CameraScreen(
    onCameraPermissionDenied: () -> Unit,
    onCameraClosed: () -> Unit,
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
            onCloseButtonClick = onCameraClosed,
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
    onCloseButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraController = remember { LifecycleCameraController(context) }

    var isTorchEnabled by rememberSaveable { mutableStateOf(false) }
    cameraController.enableTorch(isTorchEnabled)

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                AndroidView(
                    factory = { context ->
                        PreviewView(context).apply {
                            layoutParams = LinearLayout.LayoutParams(
                                MATCH_PARENT,
                                MATCH_PARENT
                            )
                            setBackgroundColor(androidColor.BLACK)
                            scaleType = PreviewView.ScaleType.FILL_START
                        }.also { previewView ->
                            previewView.controller = cameraController
                            cameraController.bindToLifecycle(lifecycleOwner)
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
                IconButton(
                    onClick = onCloseButtonClick,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart)
                ) {
                    Icon(
                        painter =  painterResource(id = designSystemR.drawable.ic_close), 
                        contentDescription = stringResource(id = designSystemR.string.navigation_close_icon_description),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(32.dp)
                    )
                }
                IconButton(
                    onClick = {
                        isTorchEnabled = !isTorchEnabled
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopEnd)
                ) {
                    val torchIconModifier = Modifier.size(32.dp)
                    val torchIconTint = MaterialTheme.colorScheme.onPrimary
                    if (!isTorchEnabled) {
                        Icon(
                            painter =  painterResource(id = R.drawable.ic_flash_on),
                            contentDescription = stringResource(id = R.string.flash_on_icon_desc),
                            tint = torchIconTint,
                            modifier = torchIconModifier,
                        )
                    } else {
                        Icon(
                            painter =  painterResource(id = R.drawable.ic_flash_off),
                            contentDescription = stringResource(id = R.string.flash_off_icon_desc),
                            tint = torchIconTint,
                            modifier = torchIconModifier,
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(composeColor.Black)
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .border(
                            width = 4.dp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            shape = CircleShape
                        )
                        .padding(12.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onPrimary)
                        .align(Alignment.Center)
                )
            }
        }
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