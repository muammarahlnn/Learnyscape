package com.muammarahlnn.learnyscape.core.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ClassResourceCard, 07/08/2023 22.39 by Muammar Ahlan Abimanyu
 */

enum class ClassResourceType(
    @StringRes val nameRes: Int,
    @DrawableRes val iconRes: Int
) {
    ANNOUNCEMENT(
        nameRes = R.string.announcement,
        iconRes = R.drawable.ic_announcement_border
    ),
    MODULE(
        nameRes = R.string.module,
        iconRes = R.drawable.ic_book_border
    ),
    ASSIGNMENT(
        nameRes = R.string.assignment,
        iconRes = R.drawable.ic_assignment_border
    ),
    QUIZ(
        nameRes = R.string.quiz,
        iconRes = R.drawable.ic_quiz_border
    );

    companion object {

        fun getClassResourceType(classResourceTypeOrdinal: Int) =
            when (classResourceTypeOrdinal) {
                ANNOUNCEMENT.ordinal -> ANNOUNCEMENT
                MODULE.ordinal -> MODULE
                ASSIGNMENT.ordinal -> ASSIGNMENT
                QUIZ.ordinal -> QUIZ
               else -> throw IllegalArgumentException("The given ordinal not matched any ClassResourceType's ordinal")
            }
    }
}

@Composable
fun ClassResourceCard(
    classResourceType: ClassResourceType,
    title: String,
    timeLabel: String,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit = {},
) {
    BaseCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(classResourceType.ordinal)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(10.dp)
            ) {
                Image(
                    painter = painterResource(id = classResourceType.iconRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                // this is a hardcoded text just for dummy purpose
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    text = timeLabel,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }
        }
    }
}
