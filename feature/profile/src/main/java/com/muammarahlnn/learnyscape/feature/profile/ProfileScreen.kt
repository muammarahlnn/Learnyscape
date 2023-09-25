package com.muammarahlnn.learnyscape.feature.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeTopAppBar
import com.muammarahlnn.learnyscape.core.designsystem.component.defaultTopAppBarColors


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ProfileScreen, 20/07/2023 21.48 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun ProfileRoute(
    onLogoutButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ProfileScreen(
        onLogoutButtonClick = onLogoutButtonClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileScreen(
    onLogoutButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        ProfileTopAppBar()
        ProfileContent(
            onLogoutButtonClick = onLogoutButtonClick,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    LearnyscapeTopAppBar(
        title = R.string.profile,
        scrollBehavior = scrollBehavior,
        colors = defaultTopAppBarColors(),
        modifier = modifier,
    )
}

@Composable
private fun ProfileContent(
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
                    onClick = {
                        // will implement later
                    },
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

            // hardcoded text just for dummy purpose
            Text(
                text = "Muammar Ahlan Abimanyu",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "H071191032",
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
                    contentDescription = stringResource(id = R.string.logout_icon_description)
                )
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = stringResource(id = R.string.logout))
            }
        }
    }
}