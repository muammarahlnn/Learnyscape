package com.muammarahlnn.learnyscape.feature.aclass

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.muammarahlnn.learnyscape.core.model.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.ClassTopAppBar
import com.muammarahlnn.learnyscape.core.ui.getClassResourceIcon


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ClassScreen, 04/08/2023 00.07 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun ClassRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ClassScreen(
        onBackClick = onBackClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ClassScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Column(modifier = modifier) {
        ClassTopAppBar(
            scrollBehavior = scrollBehavior,
            onBackClick = onBackClick,
        )
        ClassContent(scrollBehavior = scrollBehavior)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ClassContent(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        item {
            ClassHeader()
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            ClassResourcePostCard(
                classResourceType = ClassResourceType.ASSIGNMENT,
                title = "Tugas Fragment",
                timePosted = "21 May 2023",
                caption = "Lorem ipsum dolor sit amet. In quis dolore qui enim vitae hic ullam sint et magni dicta et autem commodi ea quibusdam dicta. Vel inventore",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            ClassResourcePostCard(
                classResourceType = ClassResourceType.MODULE,
                title = "Materi Background Thread",
                timePosted = "11 May 2023",
                caption = "Lorem ipsum dolor sit amet. In quis dolore qui enim vitae hic ullam sint et magni dicta et autem commodi ea quibusdam dicta. Vel inventore",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            ClassResourcePostCard(
                classResourceType = ClassResourceType.QUIZ,
                title = "Quiz Networking",
                timePosted = "10 May 2023",
                caption = "Lorem ipsum dolor sit amet. In quis dolore qui enim vitae hic ullam sint et magni dicta et autem commodi ea quibusdam dicta. Vel inventore",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            AnnouncementPostCard(
                authorName = "Andi Muh. Amil Siddik, S.Si., M.Si",
                timePosted = "2 May 2023",
                caption = "Lorem ipsum dolor sit amet. In quis dolore qui enim vitae hic ullam sint et magni dicta et autem commodi ea quibusdam dicta. Vel inventore",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ClassInfoCard(
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
private fun ClassHeader(
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
                .height(175.dp)
        )

        // can replace it with more simple layout like Column,
        // but use ConstraintLayout instead for learning purpose only
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (classIcon, classInfoCard) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.ic_groups),
                contentDescription = stringResource(id = R.string.groups),
                modifier = Modifier
                    .size(125.dp)
                    .constrainAs(classIcon) {
                        top.linkTo(parent.top)
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
private fun AnnouncementPostCard(
    authorName: String,
    timePosted: String,
    caption: String,
    modifier: Modifier = Modifier,
) {
    BasePostCard(
        indicatorImage = {
            Image(
                painter = painterResource(id = R.drawable.ava_luffy),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(36.dp)
            )
        },
        title = authorName,
        timePosted = timePosted,
        caption = caption,
        modifier = modifier,
    )
}

@Composable
fun ClassResourcePostCard(
    classResourceType: ClassResourceType,
    title: String,
    timePosted: String,
    caption: String,
    modifier: Modifier = Modifier,
) {
    BasePostCard(
        indicatorImage = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(8.dp)
            ) {
                Image(
                    painter = getClassResourceIcon(type = classResourceType),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        title = title,
        timePosted = timePosted,
        caption = caption,
        modifier = modifier,
    )
}

@Composable
private fun BasePostCard(
    indicatorImage: @Composable () -> Unit,
    title: String,
    timePosted: String,
    caption: String,
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
        modifier = modifier,
    ) {
        Column {
            // header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 12.dp,
                )
            ) {
                indicatorImage()

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    // this is a hardcoded text just for dummy purpose
                    Text(
                        text = title,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                    Text(
                        text = timePosted,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                }
            }

            Divider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.background,
            )

            // content
            Box(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = caption,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                )
            }
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