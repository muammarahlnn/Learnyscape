package com.muammarahlnn.learnyscape.feature.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseAlertDialog
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
import com.muammarahlnn.learnyscape.core.designsystem.theme.WhiteLightGray
import com.muammarahlnn.learnyscape.core.model.data.UserRole
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
        onChangePasswordButtonClick = {

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
    onChangePasswordButtonClick: () -> Unit,
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .padding(16.dp),
    ) {
        ProfileContent(
            onChangePhotoProfileButtonClick = onChangePhotoProfileButtonClick,
        )

        Spacer(modifier = Modifier.height(16.dp))

        ChangePasswordCard(
            onClick = onChangePasswordButtonClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        LogoutCard(
            onClick = onLogoutButtonClick,
        )
    }
}

@Composable
private fun ProfileContent(
    onChangePhotoProfileButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        ProfileDetailInfoCard(
            modifier = Modifier.padding(top = photoProfileSize / 2)
        )
        PhotoProfile(
            onChangePhotoProfileButtonClick = onChangePhotoProfileButtonClick,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun ProfileDetailInfoCard(modifier: Modifier = Modifier) {
    val user = LocalUserModel.current
    BaseCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    top = photoProfileSize / 2 + 16.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_info),
                    contentDescription = stringResource(id = R.string.user_info),
                    tint = MaterialTheme.colorScheme.primary,
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = when (user.role) {
                        UserRole.STUDENT -> stringResource(
                            id = R.string.student_data
                        )
                        UserRole.LECTURER -> stringResource(
                            id = R.string.lecturer_data
                        )
                        UserRole.NOT_LOGGED_IN -> ""
                    },
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            BaseProfileInfoText(
                title = stringResource(id = R.string.name),
                value = user.fullName
            )

            Spacer(modifier = Modifier.height(12.dp))

            BaseProfileInfoText(
                title = when (user.role) {
                    UserRole.STUDENT -> stringResource(
                        id = R.string.nim
                    )
                    UserRole.LECTURER -> stringResource(
                        id = R.string. nip
                    )
                    UserRole.NOT_LOGGED_IN -> ""
                },
                value = user.id
            )
        }
    }
}

@Composable
private fun BaseProfileInfoText(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(WhiteLightGray)
                .padding(
                    vertical = 16.dp,
                    horizontal = 16.dp,
                )
        )
    }
}

@Composable
private fun PhotoProfile(
    onChangePhotoProfileButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        val photoProfileModifier = Modifier
            .size(photoProfileSize)
            .clip(CircleShape)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSecondary,
                shape = CircleShape
            )

        Image(
            painter = painterResource(id = R.drawable.ava_luffy),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = photoProfileModifier
        )

        FilledIconButton(
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background,
            ),
            onClick = onChangePhotoProfileButtonClick,
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_photo_camera),
                contentDescription = stringResource(id = R.string.camera_icon_description),
                modifier = Modifier.size(20.dp),
            )
        }
    }
}

@Composable
private fun ChangePasswordCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_vpn_key),
                contentDescription = stringResource(id = R.string.change_password),
                tint = MaterialTheme.colorScheme.primary,
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = stringResource(id = R.string.change_password),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@Composable
private fun LogoutCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_logout),
                contentDescription = stringResource(id = R.string.logout),
                tint = MaterialTheme.colorScheme.primary,
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = stringResource(id = R.string.logout),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
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

private val photoProfileSize = 100.dp