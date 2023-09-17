package com.muammarahlnn.learnyscape.feature.module

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.ui.ClassResourceCard
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ModuleScreen, 03/08/2023 15.48 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun ModuleRoute(
    onModuleClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    ModuleScreen(
        onModuleClick = onModuleClick,
        modifier = modifier,
    )
}

@Composable
private fun ModuleScreen(
    onModuleClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        repeat(20) {
            item {
                ClassResourceCard(
                    classResourceType = ClassResourceType.MODULE,
                    title = "Materi Networking dan Background Thread",
                    timeLabel = "Posted 21 May 2023, 21:21",
                    onItemClick = onModuleClick,
                )
            }
        }
    }
}