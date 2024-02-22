package com.muammarahlnn.learnyscape.feature.assignmentsubmission.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseAlertDialog
import com.muammarahlnn.learnyscape.feature.assignmentsubmission.R

/**
 * @Author Muammar Ahlan Abimanyu
 * @File UnsubmitDialog, 19/02/2024 19.39
 */
@Composable
internal fun UnsubmitDialog(
    onUnsubmit: () -> Unit,
    onDismiss: () -> Unit,
) {
    BaseAlertDialog(
        title = stringResource(id = R.string.unsubmit_dialog_title),
        dialogText = stringResource(id = R.string.unsubmit_dialog_text),
        onConfirm = onUnsubmit,
        onDismiss = onDismiss,
        confirmText = stringResource(id = R.string.unsubmit),
    )
}