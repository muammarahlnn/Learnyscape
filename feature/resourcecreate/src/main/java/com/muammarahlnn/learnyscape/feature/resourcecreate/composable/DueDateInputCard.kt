package com.muammarahlnn.learnyscape.feature.resourcecreate.composable

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.muammarahlnn.learnyscape.core.ui.util.noRippleClickable
import com.muammarahlnn.learnyscape.feature.resourcecreate.R
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * @Author Muammar Ahlan Abimanyu
 * @File DueDateInputCard, 27/12/2023 00.26
 */
@Composable
internal fun DueDateInputCard(
    date: LocalDate?,
    time: LocalTime?,
    onDueDateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    InputCard(
        iconRes = R.drawable.ic_calendar,
        iconDescriptionRes = R.string.due_date,
        modifier = modifier,
    ) {
        val text = if (date == null || time == null) {
            stringResource(id = R.string.due_date)
        } else {
            val dateTime = LocalDateTime.of(date, time)
            formatDateTime(dateTime)
        }

        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .noRippleClickable {
                    onDueDateClick()
                },
        )
    }
}

fun formatDateTime(dateTime: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm")
    return formatter.format(dateTime)
}
