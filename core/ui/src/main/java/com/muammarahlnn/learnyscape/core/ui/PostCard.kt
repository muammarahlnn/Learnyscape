package com.muammarahlnn.learnyscape.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file PostCard, 21/08/2023 21.42 by Muammar Ahlan Abimanyu
 */

@Composable
fun PostCard(
    classResourceType: ClassResourceType,
    title: String,
    timePosted: String,
    caption: String,
    isCaptionOverflowed: Boolean,
    modifier: Modifier = Modifier,
    onPostClick: (Int) -> Unit = {},
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        modifier = modifier.clickable(
            enabled = isCaptionOverflowed
        ) {
            onPostClick(classResourceType.ordinal)
        },
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 12.dp,
                )
            ) {
                when (classResourceType) {
                    ClassResourceType.ANNOUNCEMENT -> {
                        Image(
                            painter = painterResource(id = R.drawable.ava_luffy),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(36.dp)
                        )
                    }

                    ClassResourceType.MODULE,
                    ClassResourceType.ASSIGNMENT,
                    ClassResourceType.QUIZ -> {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                                .padding(8.dp)
                        ) {
                            Image(
                                painter = painterResource(id = classResourceType.iconRes),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
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

            Box(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = caption,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodySmall,
                    overflow =
                        if (isCaptionOverflowed) TextOverflow.Ellipsis
                        else TextOverflow.Clip,
                    maxLines = if (isCaptionOverflowed) 2 else Int.MAX_VALUE,
                )
            }
        }
    }
}