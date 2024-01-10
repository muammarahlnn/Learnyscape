package com.muammarahlnn.learnyscape.feature.resourcecreate.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muammarahlnn.learnyscape.core.ui.util.noRippleClickable
import com.muammarahlnn.learnyscape.feature.resourcecreate.QuizType
import com.muammarahlnn.learnyscape.feature.resourcecreate.R
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR

/**
 * @Author Muammar Ahlan Abimanyu
 * @File QuizTypeInputCard, 28/12/2023 01.50
 */
@Composable
internal fun QuizTypeInputCard(
    quizType: QuizType,
    onQuizTypeClick: () -> Unit,
    onQuestionsClick: () -> Unit,
) {
    InputCard(
        iconRes = designSystemR.drawable.ic_quiz_type,
        iconDescriptionRes = R.string.quiz_type
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.noRippleClickable {
                onQuizTypeClick()
            }
        ) {
            Text(
                text = stringResource(id = when (quizType) {
                    QuizType.NONE -> R.string.quiz_type
                    QuizType.MCQ -> R.string.multiple_choice_question
                    QuizType.PHOTO_ANSWER -> R.string.photo_answer
                }),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.width(4.dp))

            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_drop_down),
                contentDescription = stringResource(id = R.string.quiz_type),
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(6.dp),
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(
                        horizontal = 10.dp,
                        vertical = 5.dp,
                    )
                    .noRippleClickable {
                        onQuestionsClick()
                    }
            ) {
                Text(
                    text = stringResource(id = R.string.questions),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 11.sp,
                    ),
                    color = MaterialTheme.colorScheme.primary,
                )

                Spacer(modifier = Modifier.width(4.dp))

                Icon(
                    painter = painterResource(id = R.drawable.ic_chevron_right),
                    contentDescription = stringResource(id = R.string.questions),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}