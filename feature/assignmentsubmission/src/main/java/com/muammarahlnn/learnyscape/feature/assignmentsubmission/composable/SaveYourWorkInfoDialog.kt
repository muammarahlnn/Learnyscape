package com.muammarahlnn.learnyscape.feature.assignmentsubmission.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseAlertDialog
import com.muammarahlnn.learnyscape.feature.assignmentsubmission.R

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SaveYourWorkInfoDialog, 19/02/2024 19.46
 */
@Composable
internal fun SaveYourWorkInfoDialog(
    onDismiss: () -> Unit,
) {
    BaseAlertDialog(
        title = stringResource(id = R.string.save_your_work_dialog_title),
        dialogText = stringResource(id = R.string.save_your_work_dialog_text),
        onConfirm = onDismiss,
        onDismiss = onDismiss,
        confirmText = stringResource(id = R.string.save_your_work_dialog_confirm_text),
        dismissText = null,
    )
}