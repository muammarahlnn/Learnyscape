package com.muammarahlnn.learnyscape.feature.aclass

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.muammarahlnn.learnyscape.core.model.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.AnnouncementPostCard
import com.muammarahlnn.learnyscape.core.ui.ClassResourcePostCard
import com.muammarahlnn.learnyscape.core.ui.ClassTopAppBar


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ClassScreen, 04/08/2023 00.07 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun ClassRoute(
    onPostClick: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ClassScreen(
        onPostClick = onPostClick,
        onBackClick = onBackClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ClassScreen(
    onPostClick: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Column(modifier = modifier) {
        ClassTopAppBar(
            scrollBehavior = scrollBehavior,
            onBackClick = onBackClick,
        )
        ClassContent(
            scrollBehavior = scrollBehavior,
            onPostClick = onPostClick,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ClassContent(
    scrollBehavior: TopAppBarScrollBehavior,
    onPostClick: (String) -> Unit,
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
                onPostClick = onPostClick,
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
                onPostClick = onPostClick,
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
                onPostClick = onPostClick,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            AnnouncementPostCard(
                authorName = "Andi Muh. Amil Siddik, S.Si., M.Si",
                timePosted = "2 May 2023",
                caption = "Lorem ipsum dolor sit amet. In quis dolore qui enim vitae hic ullam sint et magni dicta et autem commodi ea quibusdam dicta. Vel inventore",
                onPostClick = onPostClick,
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