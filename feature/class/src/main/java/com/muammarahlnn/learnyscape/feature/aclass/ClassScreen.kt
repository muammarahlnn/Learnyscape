package com.muammarahlnn.learnyscape.feature.aclass

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.ui.ClassTopAppBar


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
    Column(modifier = modifier) {
        ClassTopAppBar(onBackClick = onBackClick)
        ClassHeader()
        ClassInfoCard()
    }
}

private val headerHeight = 160.dp
private val classIconSize  = 120.dp

@Composable
private fun ClassHeader() {
    Box {
        Image(
            painter = painterResource(id = R.drawable.bg_class_header_gradient),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_groups),
            contentDescription = stringResource(id = R.string.groups),
            modifier = Modifier
                .size(classIconSize)
                .align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun ClassInfoCard(
    modifier: Modifier = Modifier,
) {
    val placementY = with(LocalDensity.current) {
        (headerHeight - classIconSize).roundToPx()
    }
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
            .padding(horizontal = 16.dp)
            .placeAt(0, -placementY)
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
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

private fun Modifier.placeAt(
    x: Int,
    y: Int,
): Modifier = composed {
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        layout(placeable.width, placeable.height) {
            placeable.placeRelative(x, y)
        }
    }
}