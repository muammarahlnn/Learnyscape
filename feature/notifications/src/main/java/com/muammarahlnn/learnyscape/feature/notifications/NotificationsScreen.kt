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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeTopAppBar
import com.muammarahlnn.learnyscape.core.designsystem.component.defaultTopAppBarColors
import com.muammarahlnn.learnyscape.core.model.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.getClassResourceIcon
import com.muammarahlnn.learnyscape.core.ui.getClassResourceName
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file NotificationsScreen, 01/08/2023 13.18 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun NotificationsRoute(
    onNotificationClick: (String) -> Unit,
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
    onNotificationClick: (String) -> Unit,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotificationContent(
    scrollBehavior: TopAppBarScrollBehavior,
    onNotificationClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) {
        // dummy list only for test ui purpose
        listOf(
            ClassResourceType.MODULE,
            ClassResourceType.ASSIGNMENT,
            ClassResourceType.QUIZ,
            ClassResourceType.MODULE,
            ClassResourceType.ASSIGNMENT,
            ClassResourceType.QUIZ,
            ClassResourceType.MODULE,
            ClassResourceType.ASSIGNMENT,
            ClassResourceType.QUIZ,
            ClassResourceType.MODULE,
            ClassResourceType.ASSIGNMENT,
            ClassResourceType.QUIZ,
        ).forEach {
            item {
                NotificationItem(
                    type = it,
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
    LearnyscapeTopAppBar(
        titleRes = R.string.notifications,
        navigationIconRes = designSystemR.drawable.ic_arrow_back_bold,
        navigationIconContentDescription = stringResource(
            id = designSystemR.string.navigation_back_icon_description
        ),
        colors = defaultTopAppBarColors(),
        scrollBehavior = scrollBehavior,
        onNavigationClick = onBackClick,
        modifier = modifier,
    )
}

@Composable
private fun NotificationItem(
    type: ClassResourceType,
    onNotificationClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val typeName = getClassResourceName(type = type)
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onNotificationClick(typeName)
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
                    painter = getClassResourceIcon(type = type),
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
                    text = "Lorem ipsum dolor sit amet lorem ipsum dolor sit amet",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                Text(
                    text = "Lorem ipsum dolor sit",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "11.45",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}
