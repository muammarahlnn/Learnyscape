package com.muammarahlnn.learnyscape.feature.resourcecreate.composable

import android.widget.Toast
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseAlertDialog
import com.muammarahlnn.learnyscape.feature.resourcecreate.R
import com.muammarahlnn.learnyscape.feature.resourcecreate.ResourceCreateContract
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SetDueDateDialog, 27/12/2023 01.05
 */
@Composable
internal fun SetDueDateDialog(
    state: ResourceCreateContract.State,
    onConfirm: (LocalDate?, LocalTime?) -> Unit,
    onDismiss: () -> Unit,
) {
    val (dueDate, dueTime) = when (state.dueDateType) {
        DueDateType.DUE_DATE -> state.dueDate
        DueDateType.START_DATE -> state.startDate
        DueDateType.END_DATE -> state.endDate
    }.run {
        date to time
    }

    SetDueDateDialog(
        title = stringResource(id = state.dueDateType.titleRes),
        dueDate = dueDate,
        dueTime = dueTime,
        onConfirm = onConfirm,
        onDismiss = onDismiss,
    )
}

@Composable
private fun SetDueDateDialog(
    title: String,
    dueDate: LocalDate?,
    dueTime: LocalTime?,
    onConfirm: (LocalDate?, LocalTime?) -> Unit,
    onDismiss: () -> Unit,
) {
    val context = LocalContext.current

    var currentDueDate: LocalDate? by rememberSaveable { mutableStateOf(dueDate) }
    var currentDueTime: LocalTime? by rememberSaveable { mutableStateOf(dueTime) }

    var showDatePickerDialog by rememberSaveable { mutableStateOf(false) }
    if (showDatePickerDialog) {
        DueDatePickerDialog(
            date = currentDueDate ?: LocalDate.now(),
            onConfirm = { selectedDate ->
                currentDueDate = selectedDate
                showDatePickerDialog = false
            },
            onDismiss = { showDatePickerDialog = false }
        )
    }

    var showTimePickerDialog by rememberSaveable { mutableStateOf(false) }
    if (showTimePickerDialog) {
        DueTimePickerDialog(
            time = currentDueTime ?: LocalTime.now(),
            onConfirm = { selectedTime ->
                currentDueTime = selectedTime
                showTimePickerDialog = false
            },
            onDismiss = { showTimePickerDialog = false }
        )
    }

    BaseAlertDialog(
        title = title,
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy")
                Text(
                    text = currentDueDate?.format(dateFormatter) ?: "Set date",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(MaterialTheme.colorScheme.outline)
                        .clickable {
                            showDatePickerDialog = true
                        }
                        .padding(
                            horizontal = 12.dp,
                            vertical = 6.dp,
                        )
                )

                Spacer(modifier = Modifier.width(12.dp))

                val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
                Text(
                    text = currentDueTime?.format(timeFormatter) ?: "Set time",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(MaterialTheme.colorScheme.outline)
                        .clickable {
                            showTimePickerDialog = true
                        }
                        .padding(
                            horizontal = 16.dp,
                            vertical = 6.dp,
                        )
                )
            }
        },
        onConfirm = {
            if (currentDueDate != null && currentDueTime != null) {
                onConfirm(currentDueDate, currentDueTime)
            } else {
                Toast.makeText(
                    context,
                    "You must set the due date and time",
                    Toast.LENGTH_SHORT
                ).show()
            }
        },
        onDismiss = onDismiss,
    )
}

enum class DueDateType(val titleRes: Int) {
    DUE_DATE(R.string.due_date),
    START_DATE(R.string.start_date),
    END_DATE(R.string.end_date),
}