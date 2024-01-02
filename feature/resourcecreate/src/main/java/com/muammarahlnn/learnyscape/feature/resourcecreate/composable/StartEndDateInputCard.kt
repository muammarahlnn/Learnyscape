package com.muammarahlnn.learnyscape.feature.resourcecreate.composable

import androidx.compose.foundation.layout.Spacer
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
import com.muammarahlnn.learnyscape.core.ui.util.noRippleClickable
import com.muammarahlnn.learnyscape.feature.resourcecreate.DueDate
import com.muammarahlnn.learnyscape.feature.resourcecreate.R
import java.time.LocalDateTime

/**
 * @Author Muammar Ahlan Abimanyu
 * @File StartEndDateInputCard, 28/12/2023 02.03
 */
@Composable
internal fun StartEndDateInputCard(
    startDate: DueDate,
    endDate: DueDate,
    onStartDateClick: () -> Unit,
    onEndDateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    InputCard(
        iconRes = R.drawable.ic_calendar,
        iconDescriptionRes = R.string.due_date,
        modifier = modifier,
    ) {
        val startDateText = if (startDate.date == null || startDate.time == null) {
            stringResource(id = R.string.start_date)
        } else {
            formatDateTime(LocalDateTime.of(startDate.date, startDate.time))
        }
        Text(
            text = startDateText,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .noRippleClickable {
                    onStartDateClick()
                },
        )
        
        Spacer(modifier = Modifier.width(4.dp))

        Icon(
            painter = painterResource(id = R.drawable.ic_horizontal_rule),
            contentDescription = stringResource(id = R.string.until),
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        Spacer(modifier = Modifier.width(4.dp))

        val endDateText = if (endDate.date == null || endDate.time == null) {
            stringResource(id = R.string.end_date)
        } else {
            formatDateTime(LocalDateTime.of(endDate.date, endDate.time))
        }
        Text(
            text = endDateText,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .noRippleClickable {
                    onEndDateClick()
                },
        )
    }
}