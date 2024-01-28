package com.muammarahlnn.learnyscape.feature.aclass.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.ui.util.LecturerOnlyComposable
import com.muammarahlnn.learnyscape.feature.aclass.R
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassNavigationAndActionTopIcons, 27/01/2024 22.45
 */

internal val iconBoxSize = 48.dp

@Composable
internal fun BoxScope.ClassNavigationAndActionTopIcons(
    onBackClick: () -> Unit,
    onJoinRequestsClick: () -> Unit,
) {
    val iconBoxModifier = Modifier.size(iconBoxSize)
    CircleBox(
        onClick = onBackClick,
        modifier = iconBoxModifier
            .align(Alignment.TopStart)
            .padding(
                top = 16.dp,
                start = 16.dp,
            )
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
            onClick = onJoinRequestsClick,
            modifier = iconBoxModifier
                .align(Alignment.TopEnd)
                .padding(
                    top = 16.dp,
                    end = 16.dp,
                ),
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
}

@Composable
private fun CircleBox(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(
                elevation = 1.dp,
                shape = CircleShape,
            )
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.background)
            .clickable { onClick() },
    ) {
        content()
    }
}