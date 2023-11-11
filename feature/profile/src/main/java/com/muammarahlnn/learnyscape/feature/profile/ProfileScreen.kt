package com.muammarahlnn.learnyscape.feature.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseAlertDialog
import com.muammarahlnn.learnyscape.core.ui.util.LocalUserModel


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ProfileScreen, 20/07/2023 21.48 by Muammar Ahlan Abimanyu
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProfileRoute(
    scrollBehavior: TopAppBarScrollBehavior,
    onCameraActionClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    var showChangePhotoProfileBottomSheet by rememberSaveable {
        mutableStateOf(false)
    }
    var showLogoutDialog by rememberSaveable {
        mutableStateOf(false)
    }
    ProfileScreen(
        scrollBehavior = scrollBehavior,
        showChangePhotoProfileBottomSheet = showChangePhotoProfileBottomSheet,
        showLogoutDialog = showLogoutDialog,
        onChangePhotoProfileButtonClick = {
            showChangePhotoProfileBottomSheet = true
        },
        onCameraActionClick = {
            onCameraActionClick()
            showChangePhotoProfileBottomSheet = false
        },
        onDismissChangePhotoProfileBottomSheet = {
          showChangePhotoProfileBottomSheet = false
        },
        onLogoutButtonClick = {
            showLogoutDialog = true
        },
        onConfirmLogoutDialog = viewModel::logout,
        onDismissLogoutDialog = {
            showLogoutDialog = false
        },
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileScreen(
    scrollBehavior: TopAppBarScrollBehavior,
    showChangePhotoProfileBottomSheet: Boolean,
    showLogoutDialog: Boolean,
    onChangePhotoProfileButtonClick: () -> Unit,
    onCameraActionClick: () -> Unit,
    onDismissChangePhotoProfileBottomSheet: () -> Unit,
    onLogoutButtonClick: () -> Unit,
    onConfirmLogoutDialog: () -> Unit,
    onDismissLogoutDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (showChangePhotoProfileBottomSheet) {
        ChangePhotoProfileBottomSheet(
            onCameraActionClick = onCameraActionClick,
            onGalleryActionClick = {
                // will implement later
            },
            onDismiss = onDismissChangePhotoProfileBottomSheet,
        )
    }

    if (showLogoutDialog) {
        LogoutDialog(
            onLogout = onConfirmLogoutDialog,
            onDismiss = onDismissLogoutDialog,
        )
    }

    ProfileContent(
        onChangePhotoProfileButtonClick = onChangePhotoProfileButtonClick,
        onLogoutButtonClick = onLogoutButtonClick,
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
    )
}

@Composable
private fun ProfileContent(
    onChangePhotoProfileButtonClick: () -> Unit,
    onLogoutButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.ava_luffy),
                    contentDescription = null,
                    modifier = Modifier
                        .size(160.dp)
                        .clip(CircleShape)
                )
                FilledIconButton(
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    onClick = onChangePhotoProfileButtonClick,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_photo_camera),
                        contentDescription = stringResource(id = R.string.camera_icon_description),
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val user = LocalUserModel.current
            Text(
                text = user.fullName,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = user.username,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onLogoutButtonClick,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_logout),
                    contentDescription = stringResource(id = R.string.logout)
                )
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = stringResource(id = R.string.logout))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChangePhotoProfileBottomSheet(
    onCameraActionClick: () -> Unit,
    onGalleryActionClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        sheetState = bottomSheetState,
        containerColor = MaterialTheme.colorScheme.onPrimary,
        onDismissRequest = onDismiss,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(0.5f)
                    .clickable {
                        onCameraActionClick()
                    },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_photo_camera_border),
                    contentDescription = stringResource(id = R.string.camera_icon_description),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.camera_icon_description),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(0.5f)
                    .clickable {
                        onGalleryActionClick()
                    },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_gallery),
                    contentDescription = stringResource(id = R.string.gallery_icon_description),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.gallery_icon_description),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}

@Composable
private fun LogoutDialog(
    onLogout: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseAlertDialog(
        title = stringResource(id = R.string.logout),
        dialogText = stringResource(id = R.string.logout_dialog_text),
        onConfirm = onLogout,
        onDismiss = onDismiss,
        confirmText = stringResource(id = R.string.logout),
        modifier = modifier,
    )
}