package com.muammarahlnn.learnyscape.feature.resourcecreate.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseAlertDialog
import com.muammarahlnn.learnyscape.core.ui.util.getCurrentDate
import com.muammarahlnn.learnyscape.core.ui.util.getCurrentTime
import com.muammarahlnn.learnyscape.feature.resourcecreate.R
import com.muammarahlnn.learnyscape.feature.resourcecreate.ResourceCreateContract
import java.time.format.DateTimeFormatter

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SetDueDateDialog, 27/12/2023 01.05
 */
@Composable
internal fun SetDueDateDialog(
    state: ResourceCreateContract.State,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onSetDateClick: () -> Unit,
    onSetTimeClick: () -> Unit,
) {
    val dueDate = if (state.dueDate != null) {
        val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy")
        formatter.format(state.dueDate)
    } else {
        getCurrentDate()
    }

    val dueTime = if (state.dueTime != null) {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        formatter.format(state.dueTime)
    } else {
        getCurrentTime()
    }

    BaseAlertDialog(
        title = stringResource(id = R.string.due_date),
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = dueDate,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(MaterialTheme.colorScheme.outline)
                        .clickable {
                            onSetDateClick()
                        }
                        .padding(
                            horizontal = 12.dp,
                            vertical = 6.dp,
                        )
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = dueTime,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(MaterialTheme.colorScheme.outline)
                        .clickable {
                            onSetTimeClick()
                        }
                        .padding(
                            horizontal = 16.dp,
                            vertical = 6.dp,
                        )
                )
            }
        },
        onConfirm = onConfirm,
        onDismiss = onDismiss,
    )
}