package com.muammarahlnn.learnyscape.feature.assignmentsubmission.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseAlertDialog
import com.muammarahlnn.learnyscape.feature.assignmentsubmission.R

/**
 * @Author Muammar Ahlan Abimanyu
 * @File TurnInDialog, 19/02/2024 19.38
 */
@Composable
internal fun TurnInDialog(
    attachmentsSize: Int,
    onTurnIn: () -> Unit,
    onDismiss: () -> Unit,
) {
    BaseAlertDialog(
        title = stringResource(id = R.string.turn_in_dialog_title),
        dialogText = stringResource(
            id = if (attachmentsSize > 1) R.string.turn_in_plural_dialog_text
            else R.string.turn_in_plural_dialog_text,
            attachmentsSize,
        ),
        onConfirm = onTurnIn,
        onDismiss = onDismiss,
        confirmText = stringResource(id = R.string.turn_in),
    )
}