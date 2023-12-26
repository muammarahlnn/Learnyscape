package com.muammarahlnn.learnyscape.feature.aclass

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImage
import com.muammarahlnn.learnyscape.core.ui.PostCard
import com.muammarahlnn.learnyscape.core.ui.util.LecturerOnlyComposable
import com.muammarahlnn.learnyscape.core.ui.util.LocalUserModel
import com.muammarahlnn.learnyscape.core.ui.util.collectInLaunchedEffect
import com.muammarahlnn.learnyscape.core.ui.util.executeForLecturer
import com.muammarahlnn.learnyscape.core.ui.util.shimmerEffect
import com.muammarahlnn.learnyscape.core.ui.util.use
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ClassScreen, 04/08/2023 00.07 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun ClassRoute(
    onBackClick: () -> Unit,
    onJoinRequestsClick: () -> Unit,
    onCreateNewAnnouncementClick: (Int) -> Unit,
    onPostClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ClassViewModel = hiltViewModel(),
) {
    val (state, event) = use(contract = viewModel)
    val user = LocalUserModel.current
    LaunchedEffect(Unit) {
        executeForLecturer(user) {
            event(ClassContract.Event.FetchProfilePic)
        }
    }

    val context = LocalContext.current
    viewModel.effect.collectInLaunchedEffect {
        when (it) {
            is ClassContract.Effect.ShowToast -> {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    ClassScreen(
        state = state,
        onBackClick = onBackClick,
        onJoinRequestsClick = onJoinRequestsClick,
        onCreateNewAnnouncementClick = onCreateNewAnnouncementClick,
        onPostClick = onPostClick,
        modifier = modifier,
    )
}

@Composable
private fun ClassScreen(
    state: ClassContract.State,
    onBackClick: () -> Unit,
    onJoinRequestsClick: () -> Unit,
    onCreateNewAnnouncementClick: (Int) -> Unit,
    onPostClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val user = LocalUserModel.current

    LazyColumn(
        modifier = modifier
    ) {
        val paddingModifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp,
        )

        item {
            ClassHeader(
                onBackClick = onBackClick,
                onJoinRequestsClick = onJoinRequestsClick,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        executeForLecturer(user) {
            item {
                CreateNewAnnouncementCard(
                    onClick = onCreateNewAnnouncementClick,
                    state = state,
                    modifier = paddingModifier,
                )
            }
        }

        item {
            PostCard(
                classResourceType = ClassResourceType.ASSIGNMENT,
                title = "Tugas Fragment",
                timePosted = "21 May 2023",
                caption = "Lorem ipsum dolor sit amet. In quis dolore qui enim vitae hic ullam sint et magni dicta et autem commodi ea quibusdam dicta. Vel inventore",
                isCaptionOverflowed = true,
                onPostClick = onPostClick,
                modifier = paddingModifier,
            )
        }

        item {
            PostCard(
                classResourceType = ClassResourceType.MODULE,
                title = "Materi Background Thread",
                timePosted = "11 May 2023",
                caption = "Lorem ipsum dolor sit amet. In quis dolore qui enim vitae hic ullam sint et magni dicta et autem commodi ea quibusdam dicta. Vel inventore",
                isCaptionOverflowed = true,
                onPostClick = onPostClick,
                modifier = paddingModifier,
            )
        }

        item {
            PostCard(
                classResourceType = ClassResourceType.QUIZ,
                title = "Quiz Networking",
                timePosted = "10 May 2023",
                caption = "Lorem ipsum dolor sit amet. In quis dolore qui enim vitae hic ullam sint et magni dicta et autem commodi ea quibusdam dicta. Vel inventore",
                isCaptionOverflowed = true,
                onPostClick = onPostClick,
                modifier = paddingModifier,
            )
        }

        item {
            PostCard(
                classResourceType = ClassResourceType.ANNOUNCEMENT,
                title = "Andi Muh. Amil Siddik, S.Si., M.Si",
                timePosted = "2 May 2023",
                caption = "Lorem ipsum dolor sit amet. In quis dolore qui enim vitae hic ullam sint et magni dicta et autem commodi ea quibusdam dicta. Vel inventore",
                isCaptionOverflowed = true,
                onPostClick = onPostClick,
                modifier = paddingModifier,
            )
        }
    }
}

@Composable
private fun ClassHeader(
    onBackClick: () -> Unit,
    onJoinRequestsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_class_header_gradient),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(215.dp)
        )

        // can replace it with more simple layout like Column,
        // but use ConstraintLayout instead for learning purpose only
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (
                arrowBackIcon,
                groupAddIcon,
                classIcon,
                classInfoCard
            ) = createRefs()


            val iconBoxModifier = Modifier.size(38.dp)
            CircleBox(
                modifier = iconBoxModifier
                    .clickable {
                        onBackClick()
                    }
                    .constrainAs(arrowBackIcon) {
                        top.linkTo(
                            anchor = parent.top,
                            margin = 16.dp,
                        )
                        start.linkTo(
                            anchor = parent.start,
                            margin = 16.dp,
                        )
                    }
            ) {
                Icon(
                    painter = painterResource(id = designSystemR.drawable.ic_arrow_back_bold),
                    contentDescription = stringResource(
                        id = designSystemR.string.navigation_back_icon_description
                    ),
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(4.dp),
                )
            }

            LecturerOnlyComposable {
                CircleBox(
                    modifier = iconBoxModifier
                        .clickable {
                            onJoinRequestsClick()
                        }
                        .constrainAs(groupAddIcon) {
                            top.linkTo(
                                anchor = parent.top,
                                margin = 16.dp,
                            )
                            end.linkTo(
                                anchor = parent.end,
                                margin = 16.dp,
                            )
                        }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_group_add_bold),
                        contentDescription = stringResource(
                            id = R.string.group_add_desc
                        ),
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(6.dp),
                    )
                }
            }

            Image(
                painter = painterResource(id = R.drawable.ic_groups),
                contentDescription = stringResource(id = R.string.groups),
                modifier = Modifier
                    .size(125.dp)
                    .constrainAs(classIcon) {
                        top.linkTo(
                            anchor = parent.top,
                            margin = 32.dp,
                        )
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            ClassInfoCard(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .constrainAs(classInfoCard) {
                        top.linkTo(classIcon.bottom)
                    }
            )
        }
    }
}

@Composable
private fun CircleBox(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.background),
    ) {
        content()
    }
}

@Composable
private fun ClassInfoCard(
    modifier: Modifier = Modifier,
) {
    BaseCard(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Pemrograman Mobile A",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = "Andi Muh. Amil Siddik, S.Si., M.Si.",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Tuesday, 13:00 - 15:40",
                style = MaterialTheme.typography.bodySmall,
                color =  MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Composable
private fun CreateNewAnnouncementCard(
    state: ClassContract.State,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseCard(
        modifier = modifier.clickable {
            onClick(ClassResourceType.ANNOUNCEMENT.ordinal)
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val photoProfileModifier = Modifier
                .size(36.dp)
                .clip(CircleShape)

            if (state.isProfilePicLoading) {
                Box(modifier = photoProfileModifier.shimmerEffect())
            } else {
                PhotoProfileImage(
                    photoProfile = state.profilePic,
                    modifier = photoProfileModifier,
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = stringResource(id = R.string.create_new_announcement_card_text),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 13.sp,
                ),
                color = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }
}

// keep this function in case we need it in the future
private fun Modifier.placeAt(
    x: Int,
    y: Int,
): Modifier = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    layout(placeable.width, placeable.height) {
        placeable.placeRelative(x, y)
    }
}