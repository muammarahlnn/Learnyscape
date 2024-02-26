package com.muammarahlnn.learnyscape.feature.notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeCenterTopAppBar
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file NotificationsScreen, 01/08/2023 13.18 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun NotificationsRoute(
    onNotificationClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NotificationsScreen(
        onNotificationClick = onNotificationClick,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotificationsScreen(
    onNotificationClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Column(modifier = modifier.fillMaxSize()) {
        NotificationsTopAppBar(
            scrollBehavior = scrollBehavior,
            onBackClick = onBackClick,
        )
        NotificationContent(
            scrollBehavior = scrollBehavior,
            onNotificationClick = onNotificationClick,
        )
    }
}

data class Notif(
    val type: ClassResourceType,
    val title: String,
    val description: String,
    val time: String,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotificationContent(
    scrollBehavior: TopAppBarScrollBehavior,
    onNotificationClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) {
        // dummy list only for test ui purpose
        listOf(
            Notif(
                type = ClassResourceType.MODULE,
                title = "There is a new module, check it out!",
                description = "Lorem Ipsum Dolor S.Kom., M.Kom. post a new module",
                time = "10.45",
            ),
            Notif(
                type = ClassResourceType.ASSIGNMENT,
                title = "There is a new assignment, check it out!",
                description = "Lorem Ipsum Dolor S.Kom., M.Kom. assign a new assignment",
                time = "14.21",
            ),
            Notif(
                type = ClassResourceType.QUIZ,
                title = "There is a new quiz, check it out!",
                description = "Lorem Ipsum Dolor S.Kom., M.Kom. create a new quiz",
                time = "08.32",
            ),
        ).forEach {
            item {
                NotificationItem(
                    type = it.type,
                    title = it.title,
                    description = it.description,
                    time = it.time,
                    onNotificationClick = onNotificationClick,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotificationsTopAppBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    LearnyscapeCenterTopAppBar(
        title = stringResource(id = R.string.notifications),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = designSystemR.drawable.ic_arrow_back),
                    contentDescription = stringResource(
                        id = designSystemR.string.navigation_back_icon_description,
                    ),
                )
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier,
    )
}

@Composable
private fun NotificationItem(
    type: ClassResourceType,
    title: String,
    description: String,
    time: String,
    onNotificationClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onNotificationClick(type.ordinal)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(10.dp)
            ) {
                Image(
                    painter = painterResource(id = type.iconRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = time,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}
