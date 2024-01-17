package com.muammarahlnn.learnyscape.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeCenterTopAppBar
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeTopAppbarDefaults
import com.muammarahlnn.learnyscape.core.ui.util.LecturerOnlyComposable
import com.muammarahlnn.learnyscape.core.ui.util.shimmerEffect
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceClassScreen, 16/12/2023 02.02
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResourceClassScreen(
    resourceTitle: String,
    onBackClick: () -> Unit,
    onCreateNewResourceClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues, TopAppBarScrollBehavior) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            LearnyscapeCenterTopAppBar(
                title = resourceTitle,
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(id = designSystemR.drawable.ic_arrow_back),
                            contentDescription = stringResource(
                                id = designSystemR.string.navigation_back_icon_description
                            )
                        )
                    }
                },
                colors = LearnyscapeTopAppbarDefaults.classTopAppBarColors(),
                scrollBehavior = scrollBehavior,
                modifier = modifier,
            )
        },
        floatingActionButton = {
            LecturerOnlyComposable {
                AddCircleButton(onClick = onCreateNewResourceClick)
            }
        },
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->
        content(paddingValues, scrollBehavior)
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
            painter = painterResource(id = designSystemR.drawable.ic_add),
            contentDescription = stringResource(id = designSystemR.string.add_icon_description),
            tint = MaterialTheme.colorScheme.background,
        )
    }
}

@Composable
fun ResourceScreenLoading() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        repeat(10) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}