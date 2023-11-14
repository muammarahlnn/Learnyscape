package com.muammarahlnn.learnyscape.core.designsystem.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file TopAppBar, 25/07/2023 13.08 by Muammar Ahlan Abimanyu
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnyscapeTopAppBar(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = title),
                color = MaterialTheme.colorScheme.onPrimary,
            )
        },
        colors = colors,
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnyscapeTopAppBar(
    title: @Composable () -> Unit,
    @DrawableRes actionIconRes: Int,
    actionIconContentDescription: String?,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onActionClick: () -> Unit = {},
) {
    TopAppBar(
        title = title,
        actions = {
            IconButton(onClick = onActionClick) {
                Icon(
                    painterResource(id = actionIconRes),
                    contentDescription = actionIconContentDescription,
                )
            }
        },
        colors = colors,
        scrollBehavior = scrollBehavior,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnyscapeTopAppBar(
    @StringRes titleRes: Int,
    @DrawableRes navigationIconRes: Int,
    navigationIconContentDescription: String?,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onNavigationClick: () -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = titleRes),
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onNavigationClick,
            ) {
                Icon(
                    painterResource(id = navigationIconRes),
                    contentDescription = navigationIconContentDescription,
                )
            }
        },
        colors = colors,
        scrollBehavior = scrollBehavior,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnyscapeTopAppBar(
    title: String,
    @DrawableRes navigationIconRes: Int,
    navigationIconContentDescription: String?,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onNavigationClick: () -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(
                onClick = onNavigationClick,
            ) {
                Icon(
                    painterResource(id = navigationIconRes),
                    contentDescription = navigationIconContentDescription,
                )
            }
        },
        colors = colors,
        scrollBehavior = scrollBehavior,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnyscapeCenterTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actionsIcon: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
            )
        },
        colors = LearnyscapeTopAppbarDefaults.defaultTopAppBarColors(),
        navigationIcon = navigationIcon,
        actions = actionsIcon,
        scrollBehavior = scrollBehavior,
        modifier = modifier,
    )
}

object LearnyscapeTopAppbarDefaults {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun homeTopAppBarColors() = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.background,
        scrolledContainerColor = MaterialTheme.colorScheme.background,
        navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
        actionIconContentColor = MaterialTheme.colorScheme.onBackground,
    )

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun defaultTopAppBarColors() = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        scrolledContainerColor = MaterialTheme.colorScheme.primary,
        navigationIconContentColor = MaterialTheme.colorScheme.background,
        actionIconContentColor = MaterialTheme.colorScheme.background,
        titleContentColor = MaterialTheme.colorScheme.background,
    )

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun classTopAppBarColors() = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.onPrimary,
        scrolledContainerColor = MaterialTheme.colorScheme.onPrimary,
        navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
        actionIconContentColor = MaterialTheme.colorScheme.onBackground,
        titleContentColor = MaterialTheme.colorScheme.onBackground,
    )
}