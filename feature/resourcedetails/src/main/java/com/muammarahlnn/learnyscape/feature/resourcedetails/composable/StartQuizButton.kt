package com.muammarahlnn.learnyscape.feature.resourcedetails.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
import com.muammarahlnn.learnyscape.core.ui.util.noRippleClickable
import com.muammarahlnn.learnyscape.feature.resourcedetails.R
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR

/**
 * @Author Muammar Ahlan Abimanyu
 * @File StartQuizButton, 28/01/2024 15.34
 */
@Composable
internal fun StartQuizButton(
    startQuizDate: String,
    endQuizDate: String,
    isQuizTaken: Boolean,
    onButtonClick: () -> Unit,
    onButtonGloballyPositioned: (LayoutCoordinates) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showCanNotStartQuizDialog by remember { mutableStateOf(false) }
    if (showCanNotStartQuizDialog) {
        CanNotStartQuizDialog(
            quizStartDate = startQuizDate,
            quizEndDate = endQuizDate,
            onDismiss = { showCanNotStartQuizDialog = false }
        )
    }

    val isInStartQuizTime = isCurrentTimeInIntervalTime(startQuizDate, endQuizDate)
    when {
        isQuizTaken -> BottomCard(
            onButtonGloballyPositioned = onButtonGloballyPositioned,
            modifier = modifier,
        ) {
            Text(
                text = stringResource(id = R.string.you_have_taken_this_quiz),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.tertiary
            )

            Spacer(modifier = Modifier.width(4.dp))
            
            Icon(
                painter = painterResource(id = designSystemR.drawable.ic_check),
                contentDescription = stringResource(
                    id = R.string.you_have_taken_this_quiz
                ),
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.size(24.dp)
            )
        }

        isInStartQuizTime -> BaseActionButton(
            onButtonClick = onButtonClick,
            onButtonGloballyPositioned = onButtonGloballyPositioned,
            modifier = modifier,
        ) {
            Text(text = stringResource(id = R.string.start_quiz))
        }

        else -> BottomCard(
            onButtonGloballyPositioned = onButtonGloballyPositioned,
            modifier = modifier,
        ) {
            Button(
                onClick = {},
                enabled = false,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = stringResource(id = R.string.start_quiz))
            }

            Spacer(modifier = Modifier.width(4.dp))

            Icon(
                painter = painterResource(id = R.drawable.ic_help),
                contentDescription = stringResource(
                    id = R.string.can_not_start_quiz_dialog_title
                ),
                tint = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.noRippleClickable {
                    showCanNotStartQuizDialog = true
                }
            )
        }
    }
}

@Composable
private fun BottomCard(
    onButtonGloballyPositioned: (LayoutCoordinates) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    BaseCard(
        shape = RoundedCornerShape(
            topStart = 8.dp,
            topEnd = 8.dp,
        ),
        modifier = modifier
            .onGloballyPositioned { layoutCoordinates ->
                onButtonGloballyPositioned(layoutCoordinates)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 8.dp,
                ),
        ) {
            content()
        }
    }
}

private fun isCurrentTimeInIntervalTime(
    startDateTime: String,
    endDateTime: String,
): Boolean {

    fun getEpochSeconds(date: String): Long {
        val formatter = DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm")
        val localDateTime = LocalDateTime.parse(date, formatter)
        return localDateTime.toEpochSecond(ZoneOffset.UTC)
    }

    val startEpochSeconds = getEpochSeconds(startDateTime)
    val endEpochSeconds = getEpochSeconds(endDateTime)
    val currentEpochSeconds = Instant.now().epochSecond

    return currentEpochSeconds in startEpochSeconds..endEpochSeconds
}