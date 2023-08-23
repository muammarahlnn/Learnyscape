package com.muammarahlnn.learnyscape.feature.resourcedetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeTopAppBar
import com.muammarahlnn.learnyscape.core.designsystem.component.defaultTopAppBarColors
import com.muammarahlnn.learnyscape.core.model.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.AnnouncementPostCard
import com.muammarahlnn.learnyscape.core.ui.ClassResourcePostCard
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR
import com.muammarahlnn.learnyscape.core.ui.R as uiR


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ResourceDetailsScreen, 18/08/2023 00.48 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun ResourceDetailsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ResourceDetailsViewModel = hiltViewModel(),
) {
    val resourceType = viewModel.resourceType
    ResourceDetailsScreen(
        resourceType = resourceType,
        onBackClick = onBackClick,
        modifier = modifier,
    )
}

@Composable
private fun ResourceDetailsScreen(
    resourceType: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        ResourceDetailsTopAppBar(
            title = resourceType,
            onBackClick = onBackClick,
        )

        ResourceDetailsContent(resourceType = resourceType)
    }
}

@Composable
private fun ResourceDetailsContent(
    resourceType: String,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier,
    ) {
        item {
            DetailPostCard(resourceType = resourceType)
        }

        item {
            AttachmentCard()
        }
    }
}

@Composable
private fun DetailPostCard(
    resourceType: String,
    modifier: Modifier = Modifier,
) {
    if (resourceType == stringResource(id = uiR.string.announcement)) {
        AnnouncementPostCard(
            authorName = "Andi Muh. Amil Siddik, S.Si., M.Si",
            timePosted = "2 May 2023",
            caption = "Lorem ipsum dolor sit amet. In quis dolore qui enim vitae hic ullam sint et magni dicta et autem commodi ea quibusdam dicta. Vel inventore",
            isCaptionOverflowed = false,
            modifier = modifier,
        )
    } else {
        val classResourceType = when (resourceType) {
            stringResource(id = uiR.string.module) -> ClassResourceType.MODULE
            stringResource(id = uiR.string.assignment) -> ClassResourceType.ASSIGNMENT
            stringResource(id = uiR.string.quiz) -> ClassResourceType.QUIZ
            else -> throw IllegalArgumentException("ResourceType arguments not match any ClassResourceType")
        }

        ClassResourcePostCard(
            classResourceType = classResourceType,
            title = "Lorem Ipsum Dolor Sit Amet",
            timePosted = "10 May 2023",
            caption = "Lorem ipsum dolor sit amet. In quis dolore qui enim vitae hic ullam sint et magni dicta et autem commodi ea quibusdam dicta. Vel inventore",
            isCaptionOverflowed = false,
            modifier = modifier,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AttachmentCard(
    modifier: Modifier = Modifier,
) {
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
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.attachments),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 13.sp,
                )
            )
            
            Spacer(modifier = Modifier.height(8.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                maxItemsInEachRow = 2,
            ) {
                repeat(3) {
                    val itemModifier = Modifier
                        .padding(bottom = 8.dp)
                        .weight(0.5f)
                    AttachmentItem(
                        name = "Networking.pdf",
                        modifier = itemModifier,
                    )
                    AttachmentItem(
                        name = "Background Thread.pdf",
                        modifier = itemModifier,
                    )
                }
            }
        }
    }
}

@Composable
private fun AttachmentItem(
    name: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Card(
            shape = RoundedCornerShape(7.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onTertiary,
            ),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_document),
                    contentDescription = stringResource(id = R.string.document_icon_description),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.Center),
                )
            }
        }
        
        Spacer(modifier = Modifier.height(2.dp))
        
        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ResourceDetailsTopAppBar(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LearnyscapeTopAppBar(
        title = title,
        navigationIconRes = designSystemR.drawable.ic_arrow_back_bold,
        navigationIconContentDescription = stringResource(
            id = designSystemR.string.navigation_back_icon_description,
        ),
        colors = defaultTopAppBarColors(),
        onNavigationClick = onBackClick,
        modifier = modifier,
    )
}