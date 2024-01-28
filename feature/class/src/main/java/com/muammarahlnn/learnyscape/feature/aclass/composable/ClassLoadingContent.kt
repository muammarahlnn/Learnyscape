package com.muammarahlnn.learnyscape.feature.aclass.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.ui.util.shimmerEffect

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassLoadingContent, 27/01/2024 22.46
 */

internal val classHeaderHeight = 215.dp

@Composable
fun ClassLoadingContent(
    onBackClick: () -> Unit,
    onJoinRequestsClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(classHeaderHeight)
                .shimmerEffect()
        ) {
            ClassNavigationAndActionTopIcons(
                onBackClick = onBackClick,
                onJoinRequestsClick = onJoinRequestsClick,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(16.dp))

        repeat(3) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}