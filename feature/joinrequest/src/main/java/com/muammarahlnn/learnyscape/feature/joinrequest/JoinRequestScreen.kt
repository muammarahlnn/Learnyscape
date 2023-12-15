package com.muammarahlnn.learnyscape.feature.joinrequest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeCenterTopAppBar
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImage
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR

/**
 * @Author Muammar Ahlan Abimanyu
 * @File JoinRequestScreen, 16/12/2023 02.36
 */
@Composable
internal fun JoinRequestRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    JoinRequestScreen(
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JoinRequestScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Column(modifier = modifier.fillMaxSize()) {
        JoinRequestTopAppBar(
            onBackClick = onBackClick,
            scrollBehavior = scrollBehavior
        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            repeat(10) {
                item {
                    JoinRequestCard()
                }
            }
        }
    }
}

@Composable
private fun JoinRequestCard(
    modifier: Modifier = Modifier,
) {
    BaseCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp),
        ) {
            PhotoProfileImage(
                photoProfile = null,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Muammar Ahlan Abimanyu",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            RejectButton()

            Spacer(modifier = Modifier.width(8.dp))

            AcceptButton()
        }
    }
}

@Composable
private fun AcceptButton(
    modifier: Modifier = Modifier,
) {
    CircleButton(
        backgroundColor = MaterialTheme.colorScheme.tertiary,
        modifier = modifier,
    ) {
        Icon(
            painter = painterResource(id = designSystemR.drawable.ic_check),
            contentDescription = stringResource(id = R.string.accept_request),
            tint = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(6.dp),
        )
    }
}

@Composable
private fun RejectButton(
    modifier: Modifier = Modifier,
) {
    CircleButton(
        backgroundColor = MaterialTheme.colorScheme.primary,
        modifier = modifier,
    ) {
        Icon(
            painter = painterResource(id = designSystemR.drawable.ic_close),
            contentDescription = stringResource(id = R.string.reject_request),
            tint = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(6.dp),
        )
    }
}

@Composable
private fun CircleButton(
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(CircleShape)
            .size(32.dp)
            .background(backgroundColor),
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JoinRequestTopAppBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    LearnyscapeCenterTopAppBar(
        title = stringResource(id = R.string.join_requests),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = designSystemR.drawable.ic_arrow_back),
                    contentDescription = stringResource(id = designSystemR.string.navigation_back_icon_description),
                )
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier,
    )
}