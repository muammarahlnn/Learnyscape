package com.muammarahlnn.learnyscape.feature.resourcedetails.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
import com.muammarahlnn.learnyscape.core.model.ui.QuizType
import com.muammarahlnn.learnyscape.feature.resourcedetails.R

/**
 * @Author Muammar Ahlan Abimanyu
 * @File QuizDetailsCard, 28/01/2024 15.36
 */
@Composable
internal fun QuizDetailsCard(
    quizStartTime: String,
    quizDuration: String,
    quizType: QuizType,
    modifier: Modifier = Modifier,
) {
    BaseCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.details),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 13.sp,
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_play_arrow),
                    contentDescription = stringResource(id = R.string.start_icon_description),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(24.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = quizStartTime,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = com.muammarahlnn.learnyscape.core.designsystem.R.drawable.ic_timer),
                    contentDescription = stringResource(id = R.string.timer_icon_description),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(24.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = quizDuration,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = com.muammarahlnn.learnyscape.core.designsystem.R.drawable.ic_quiz_type),
                    contentDescription = stringResource(id = R.string.quiz_type_icon_description),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(24.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(
                        id = when (quizType) {
                            QuizType.MULTIPLE_CHOICE_QUESTIONS -> R.string.multiple_choice_questions
                            QuizType.PHOTO_ANSWER -> R.string.photo_answer
                        }
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}