package com.muammarahlnn.learnyscape.feature.resourcecreate.composable

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.feature.resourcecreate.R
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/**
 * @Author Muammar Ahlan Abimanyu
 * @File DueDatePickerDialog, 27/12/2023 00.52
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DueDatePickerDialog(
    date: LocalDate,
    onConfirm: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = date
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    )
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToLocalDate(it)
    } ?: LocalDate.now()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(selectedDate)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.due_date_picker_dialog_confirm_text),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = stringResource(id = R.string.due_date_picker_dialog_cancel_text),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
        },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.shadow(
            elevation = 2.dp,
            shape = RoundedCornerShape(8.dp),
        )
    ) {
        DatePicker(state = datePickerState)
    }
}

private fun convertMillisToLocalDate(millis: Long): LocalDate =
    Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()