package com.muammarahlnn.learnyscape.core.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file Navigation, 21/07/2023 23.46 by Muammar Ahlan Abimanyu
 */

@Composable
fun LearnyscapeNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(
        contentColor = LearnyscapeNavigationDefaults.navigationContentColor(),
        tonalElevation = 0.dp,
        containerColor = MaterialTheme.colorScheme.background,
        content = content,
        modifier = modifier
            .shadow(
                elevation = 16.dp,
            ),
    )
}

@Composable
fun RowScope.LearnyscapeNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = LearnyscapeNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = LearnyscapeNavigationDefaults.navigationContentColor(),
            selectedTextColor = LearnyscapeNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = LearnyscapeNavigationDefaults.navigationContentColor(),
            indicatorColor = LearnyscapeNavigationDefaults.navigationIndicatorColor(),
        )
    )
}

object LearnyscapeNavigationDefaults {

    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.surfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.primary

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.background
}