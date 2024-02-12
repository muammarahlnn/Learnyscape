package com.muammarahlnn.learnyscape.feature.resourcedetails.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseAlertDialog
import com.muammarahlnn.learnyscape.feature.resourcedetails.R

/**
 * @Author Muammar Ahlan Abimanyu
 * @File CanNotStartQuizDialog, 12/02/2024 16.14
 */
@Composable
internal fun CanNotStartQuizDialog(
    quizStartDate: String,
    quizEndDate: String,
    onDismiss: () -> Unit,
) {
    BaseAlertDialog(
        title = stringResource(id = R.string.can_not_start_quiz_dialog_title),
        dialogText = stringResource(
            id = R.string.can_not_start_quiz_dialog_text,
            quizStartDate,
            quizEndDate,
        ),
        onConfirm = onDismiss,
        onDismiss = onDismiss,
        confirmText = stringResource(id = R.string.save_your_work_dialog_confirm_text),
        dismissText = null,
    )
}