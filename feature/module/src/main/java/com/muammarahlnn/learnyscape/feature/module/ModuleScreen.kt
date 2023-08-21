package com.muammarahlnn.learnyscape.feature.module

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.model.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.ClassResourceCard
import com.muammarahlnn.learnyscape.core.ui.ClassTopAppBar


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ModuleScreen, 03/08/2023 15.48 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun ModuleRoute(
    onModuleClick: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ModuleScreen(
        onModuleClick = onModuleClick,
        onBackClick = onBackClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModuleScreen(
    onModuleClick: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Column(modifier = modifier.fillMaxSize()) {
        ClassTopAppBar(
            scrollBehavior = scrollBehavior,
            onBackClick = onBackClick
        )
        ModuleContent(
            scrollBehavior = scrollBehavior,
            onModuleClick = onModuleClick,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModuleContent(
    scrollBehavior: TopAppBarScrollBehavior,
    onModuleClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val resourceType = stringResource(id = R.string.module)
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        repeat(20) {
            item {
                ClassResourceCard(
                    classResourceType = ClassResourceType.MODULE,
                    title = "Materi Networking dan Background Thread",
                    timeLabel = "Posted 21 May 2023, 21:21",
                    onItemClick = {
                        onModuleClick(resourceType)
                    },
                )
            }
        }
    }
}