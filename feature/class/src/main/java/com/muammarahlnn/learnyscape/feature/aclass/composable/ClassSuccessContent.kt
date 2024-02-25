package com.muammarahlnn.learnyscape.feature.aclass.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.model.data.ClassDetailsModel
import com.muammarahlnn.learnyscape.core.model.data.ClassFeedModel
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImageUiState
import com.muammarahlnn.learnyscape.core.ui.PostCard
import com.muammarahlnn.learnyscape.core.ui.PullRefreshScreen
import com.muammarahlnn.learnyscape.core.ui.util.LocalUserModel
import com.muammarahlnn.learnyscape.core.ui.util.RefreshState
import com.muammarahlnn.learnyscape.core.ui.util.executeForLecturer
import com.muammarahlnn.learnyscape.feature.aclass.R

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassSuccessContent, 27/01/2024 22.49
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ClassSuccessContent(
    refreshState: RefreshState,
    profilePicUiState: PhotoProfileImageUiState,
    classDetails: ClassDetailsModel,
    classFeeds: List<ClassFeedModel>,
    announcementAuthorProfilePicUiStateMap: Map<Int, PhotoProfileImageUiState>,
    onBackClick: () -> Unit,
    onJoinRequestsClick: () -> Unit,
    onCreateNewAnnouncementClick: () -> Unit,
    onFeedClick: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val user = LocalUserModel.current
    PullRefreshScreen(
        pullRefreshState = refreshState.pullRefreshState,
        refreshing = refreshState.refreshing,
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            val cardPaddingModifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp,
            )

            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = R.drawable.bg_class_header_gradient),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(classHeaderHeight)
                    )

                    ClassNavigationAndActionTopIcons(
                        onBackClick = onBackClick,
                        onJoinRequestsClick = onJoinRequestsClick,
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = iconBoxSize - 16.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_groups),
                            contentDescription = stringResource(id = R.string.groups),
                            modifier = Modifier.size(120.dp)
                        )

                        ClassDetailsCard(
                            classDetails = classDetails,
                            modifier = cardPaddingModifier
                        )
                    }
                }
            }


            executeForLecturer(user) {
                item {
                    CreateNewAnnouncementCard(
                        profilePicUiState = profilePicUiState,
                        onClick = onCreateNewAnnouncementClick,
                        modifier = cardPaddingModifier
                    )
                }
            }

            if (classFeeds.isNotEmpty()) {
                itemsIndexed(
                    items = classFeeds,
                    key = { _, feed -> feed.id },
                ) { index, feed ->
                    val authorProfilePicUiState = if (feed.type == ClassFeedModel.FeedType.ANNOUNCEMENT) {
                        announcementAuthorProfilePicUiStateMap[index]
                            ?: PhotoProfileImageUiState.Success(null)
                    } else {
                        PhotoProfileImageUiState.Success(null)
                    }

                    PostCard(
                        classResourceType = when (feed.type) {
                            ClassFeedModel.FeedType.ANNOUNCEMENT -> ClassResourceType.ANNOUNCEMENT
                            ClassFeedModel.FeedType.MODULE -> ClassResourceType.MODULE
                            ClassFeedModel.FeedType.ASSIGNMENT -> ClassResourceType.ASSIGNMENT
                            ClassFeedModel.FeedType.QUIZ -> ClassResourceType.QUIZ
                        },
                        profilePicUiState = authorProfilePicUiState,
                        title = "TODO: need title from BE",
                        timePosted = feed.updatedAt,
                        caption = feed.description,
                        isCaptionOverflowed = true,
                        onPostClick = { resourceTypeOrdinal ->
                            onFeedClick(feed.uri, resourceTypeOrdinal)
                        },
                        modifier = cardPaddingModifier,
                    )
                }
            } else {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = stringResource(id = R.string.empty_feeds),
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center,
                        modifier = cardPaddingModifier
                    )
                }
            }
        }
    }
}