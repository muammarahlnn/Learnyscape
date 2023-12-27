package com.muammarahlnn.learnyscape.feature.resourcecreate.composable

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseAlertDialog
import com.muammarahlnn.learnyscape.feature.resourcecreate.R
import java.time.LocalTime

/**
 * @Author Muammar Ahlan Abimanyu
 * @File DueTimePickerDialog, 27/12/2023 03.49
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DueTimePickerDialog(
    time: LocalTime,
    onConfirm: (LocalTime) -> Unit,
    onDismiss: () -> Unit,
) {
    val timePickerState = rememberTimePickerState(
        initialHour = time.hour,
        initialMinute = time.minute,
        is24Hour = true,
    )

    val inputtedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)

    BaseAlertDialog(
        title = {
            Text(
                text = stringResource(id = R.string.due_time_picker_dialog_title),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
        },
        content = {
            TimeInput(
                state = timePickerState,
                colors = TimePickerDefaults.colors(
                    clockDialColor = MaterialTheme.colorScheme.outline,
                    clockDialSelectedContentColor = MaterialTheme.colorScheme.background,
                    clockDialUnselectedContentColor = MaterialTheme.colorScheme.onSurface,
                    selectorColor = MaterialTheme.colorScheme.primary,
                    timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.primary,
                    timeSelectorSelectedContentColor = MaterialTheme.colorScheme.background,
                    timeSelectorUnselectedContainerColor = MaterialTheme.colorScheme.outline,
                    timeSelectorUnselectedContentColor = MaterialTheme.colorScheme.onSurface,
                )
            )
        },
        onConfirm = {
            onConfirm(inputtedTime)
        },
        onDismiss = onDismiss,
        confirmText = stringResource(id = R.string.due_date_picker_dialog_confirm_text),
        dismissText = stringResource(id = R.string.due_date_picker_dialog_cancel_text),
    )
}
