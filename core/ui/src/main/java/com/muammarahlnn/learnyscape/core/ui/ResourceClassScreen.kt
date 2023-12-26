package com.muammarahlnn.learnyscape.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.muammarahlnn.learnyscape.core.designsystem.R
import com.muammarahlnn.learnyscape.core.ui.util.LecturerOnlyComposable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceClassScreen, 16/12/2023 02.02
 */
@Composable
fun ResourceClassScreen(
    onCreateNewResourceClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            LecturerOnlyComposable {
                AddCircleButton(onClick = onCreateNewResourceClick)
            }
        },
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->
        content(paddingValues)
    }
}

@Composable
private fun AddCircleButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = stringResource(id = R.string.add_icon_description),
            tint = MaterialTheme.colorScheme.background,
        )
    }
}